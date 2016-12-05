/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import java.util.Date;
import javax.faces.bean.SessionScoped;




/**
 *
 * @author vyass
 */
@ManagedBean
@SessionScoped
public class Winner {
    private double id;
    private String userID;
    private double subID;
    private boolean paid;
    

    /**
     * @return the id
     */
    public double getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(double id) {
        this.id = id;
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return the subID
     */
    public double getSubID() {
        return subID;
    }

    /**
     * @param subID the subID to set
     */
    public void setSubID(double subID) {
        this.subID = subID;
    }

    /**
     * @return the paid
     */
    public boolean isPaid() {
        return paid;
    }

    /**
     * @param paid the paid to set
     */
    public void setPaid(boolean paid) {
        this.paid = paid;
    }

}
