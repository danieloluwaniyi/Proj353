/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.ProfileDAO;
import dao.SubmissionDAO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.inject.Named;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
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
@Named(value = "submissionBean")
@SessionScoped
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

    public int getNumRaters() {
        return numRaters;
    }

    public void setNumRaters(int numRaters) {
        this.numRaters = numRaters;
    }

    /**
     * Creates a new instance of SubmissionBean
     */
    public Submission() {
        numRaters = 0;
    }
    
    public Submission(double rating, byte[] content,int submissionId,double price,int numRaters){
        this.submissionId = submissionId;
        this.rating = rating;
        this.content = content;
        this.price = price;
    }

    public Submission(double rating, byte[] content, int submissionId, double price, int numRaters) {
        this.submissionId = submissionId;
        this.rating = rating;
        this.content = content;
        this.price = price;

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
            return new DefaultStreamedContent();
        } else {
            String id = context.getExternalContext().getRequestParameterMap().get("sid");
            byte[] image = new SubmissionDAO().getSubmissionContent(id);
            return new DefaultStreamedContent(new ByteArrayInputStream(image));
        }
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
        if (submissionList == null) {
            ArrayList sub = (new SubmissionDAO().findAllSubmissions());
            this.submissionList = sub;
        }
        return submissionList;
    }

    /**
     * @param submissionList the submissionList to set
     */
    public void setSubmissionList(List<Submission> submissionList) {
        this.submissionList = submissionList;
    }
    
    
    //Insert an image into the Submission Database
    public void insertImage(FileUploadEvent event){
        this.fileUpload  = event.getFile();
      
        SubmissionDAO dao = new SubmissionDAO();
        try {
            // byte[]array = fileUpload.getContents();
            byte[] array = IOUtils.toByteArray(fileUpload.getInputstream());
        } catch (IOException ex) {
            Logger.getLogger(Submission.class.getName()).log(Level.SEVERE, null, ex);
        }
        dao.insertImage(fileUpload.getContents());
        displayUploadMsg(event);

    }

    public void updateRating(RateEvent rateEvent) {
        SubmissionDAO sDAO = new SubmissionDAO();
        calcRating(((Integer) rateEvent.getRating()));
        int rowCount = sDAO.updateRating(getSubmissionId(), rating, numRaters);

    }

    private void calcRating(double rating) {
        numRaters = numRaters + 1;
        double newRating = (this.rating + rating) / numRaters;
        this.rating = newRating;
    }
    
      public void updateRating(RateEvent rateEvent) {
        SubmissionDAO sDAO= new SubmissionDAO();
        calcRating(((Integer)rateEvent.getRating()));
        int rowCount = sDAO.updateRating(getSubmissionId(),rating,numRaters);

        
    }

      private void calcRating(double rating){
          numRaters = numRaters+1;
          double newRating = (this.rating+rating)/numRaters;
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
        return (int) rating;
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
}
