/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.SubmissionDAO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.*;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.RateEvent;

/**
 *
 * @author Daniel
 */
@ApplicationScoped
@Named(value = "submissionBean")

public class Submission implements Serializable {

    private double rating;
    private StreamedContent submission;
    private int submissionId;
    private byte[] content;
    private List<Submission> submissionList;
    private Integer intRating;
    private UploadedFile fileUpload;
    private double price;
    private int numRaters;
    private String tags;
    private int searchParam;//1->all, 2->tags, 3->tbd
    private String formattedPrice;
    private Profile profile;
    private String username;
    

    /**
     * Creates a new instance of SubmissionBean
     */
    public Submission() {
        numRaters = 0;
        searchParam = 1;

    }

    public Submission(String username,double rating, byte[] content, int submissionId, double price, int numRaters, String tags) {
        this.submissionId = submissionId;
        this.rating = rating;
        this.content = content;
        this.price = price;
        this.tags = tags;
        this.numRaters = numRaters;
        this.username = username;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public int getNumRaters() {
        return numRaters;
    }

    public void setNumRaters(int numRaters) {
        this.numRaters = numRaters;
    }

    /**
     * @return the rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * @return the submission
     */
    public StreamedContent getSubmission() {

        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            submission = new DefaultStreamedContent();
        } else {
            String id = context.getExternalContext().getRequestParameterMap().get("sid");
            byte[] image = new SubmissionDAO().getSubmissionContent(id);
            submission = new DefaultStreamedContent(new ByteArrayInputStream(image));

        }
        return submission;
    }

    /**
     * @param submission the submission to set
     */
    public void setSubmission(StreamedContent submission) {
        this.submission = submission;
    }

    /**
     * @return the submissionList
     */
    public List<Submission> getSubmissionList() {

        ArrayList sub = (new SubmissionDAO().findAllSubmissions(searchParam, tags));
        //this.searchParam = 1;
        this.submissionList = sub;
        return submissionList;
    }

    public String filteredList() {
        this.searchParam = 2;
        ArrayList sub = (new SubmissionDAO().findAllSubmissions(searchParam, tags));
        this.submissionList = sub;
        return "dashboard.xhtml?faces-redirect=true";
    }
    
    public String winnerList() {
        this.searchParam = 3;
        ArrayList sub = (new SubmissionDAO().findAllSubmissions(searchParam, tags));
        this.submissionList = sub;
        // return sub;
        return "dashboard.xhtml?faces-redirect=true";
    }

    public String fullList() {
        this.searchParam = 1;
        ArrayList sub = (new SubmissionDAO().findAllSubmissions(searchParam, tags));
        this.submissionList = sub;
        return "dashboard.xhtml?faces-redirect=true";
    }
    
    public String filter(){
        int val = this.searchParam;
        switch(val){
            case 1:{
               return fullList();
            }
            case 3:{
              return  winnerList();
            }
            
            default:
                return "dashboard.xhtml?faces-redirect=true";
        }
    }

    /**
     * @param submissionList the submissionList to set
     */
    public void setSubmissionList(List<Submission> submissionList) {
        this.submissionList = submissionList;
    }

    //Insert an image into the Submission Database
    public void insertImage(FileUploadEvent event) {
        this.fileUpload = event.getFile();

        SubmissionDAO dao = new SubmissionDAO();
        try {
            // byte[]array = fileUpload.getContents();
            byte[] array = IOUtils.toByteArray(fileUpload.getInputstream());
        } catch (IOException ex) {
            Logger.getLogger(Submission.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> param = fc.getExternalContext().getRequestParameterMap();
        Map<String, Object> params = fc.getExternalContext().getSessionMap();
        Object sessionProfile = params.get("profile");
        this.profile = (Profile) sessionProfile;

        String user = profile.getUserID();
        this.tags = param.get("form:j_idt21");
        dao.insertImage(fileUpload.getContents(), user, this.tags);
        displayUploadMsg(event);

    }

    public void updateRating(RateEvent rateEvent) {
        FacesContext fc = FacesContext.getCurrentInstance();

        Map<String, Object> params = fc.getExternalContext().getSessionMap();
        Object sessionProfile = params.get("profile");
        this.profile = (Profile) sessionProfile;
        String userId = profile.getUserID();

        SubmissionDAO sDAO = new SubmissionDAO();
        int check = sDAO.searchSubmissionRaters(userId,this.submissionId);
        if(check==0){
            calcRating(((Integer) rateEvent.getRating()));
            int rowCount = sDAO.updateRating(getSubmissionId(), rating, numRaters);
            sDAO.insertSubmissionRaters(userId, this.submissionId);
        }
        
        

    }

    private void calcRating(double rating) {
        double newRating;
        if (numRaters == 0) {
            newRating = rating;
        } else {
            newRating = (this.rating + rating) / 2;
        }
        numRaters = numRaters + 1;

        this.rating = newRating;
    }

    /**
     * @return the submissionId
     */
    public int getSubmissionId() {
        return submissionId;
    }

    /**
     * @param submissionId the submissionId to set
     */
    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }

    /**
     * @return the intRating
     */
    public Integer getIntRating() {
        intRating = (int) rating;
        return intRating;
    }

    /**
     * @param intRating the intRating to set
     */
    public void setIntRating(Integer intRating) {
        this.intRating = intRating;
    }

    public UploadedFile getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(UploadedFile fileUpload) {
        this.fileUpload = fileUpload;
    }

    private void displayUploadMsg(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getFormattedPrice() {
        NumberFormat formatter = new DecimalFormat("#0.00");

        return formatter.format(price);

    }

    public void setFormattedPrice(String formattedPrice) {
        this.formattedPrice = formattedPrice;
    }

    public int getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(int searchParam) {
        this.searchParam = searchParam;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    

}
