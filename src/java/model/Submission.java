/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.inject.Named;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;
import org.primefaces.model.*;


/**
 *
 * @author Daniel
 */
@Named(value = "submissionBean")
@SessionScoped
public class Submission implements Serializable {

    private double rating;
    private StreamedContent submission;
    private String submissionId;
    private List<Submission> submissionList;
    /**
     * Creates a new instance of SubmissionBean
     */
    public Submission() {
        
        
        
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
        return submission;
    }

    /**
     * @param submission the submission to set
     */
    public void setSubmission(StreamedContent submission) {
        this.submission = submission;
    }

    /**
     * @return the submissionId
     */
    public String getSubmissionId() {
        return submissionId;
    }

    /**
     * @param submissionId the submissionId to set
     */
    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }

    /**
     * @return the submissionList
     */
    public List<Submission> getSubmissionList() {
        return submissionList;
    }

    /**
     * @param submissionList the submissionList to set
     */
    public void setSubmissionList(List<Submission> submissionList) {
        this.submissionList = submissionList;
    }
    
}
