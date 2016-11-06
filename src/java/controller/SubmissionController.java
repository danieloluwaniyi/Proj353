/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Submission;

/**
 *
 * @author it353f616
 */
@ManagedBean
@SessionScoped
public class SubmissionController {
    
    private Submission theModel;
    

    /**
     * Creates a new instance of SubmissionController
     */
    public SubmissionController() {
        theModel = new Submission();
    }

    /**
     * @return the theModel
     */
    public Submission getTheModel() {
        return theModel;
    }

    /**
     * @param theModel the theModel to set
     */
    public void setTheModel(Submission theModel) {
        this.theModel = theModel;
    }
}