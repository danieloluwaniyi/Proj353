/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProfileDAO;
import javax.faces.bean.SessionScoped;
import model.Profile;
import javax.faces.bean.ManagedBean;
/**
 *
 * @author Suguru, Daniel, Sneh
 */
@ManagedBean
@SessionScoped
public class ProfileController {

    private Profile profile;
    private boolean skip;

    public ProfileController() {
        profile = new Profile();
    }

    /**
     * @return the profile
     */
    public Profile getProfile() {
        return profile;
    }
    
        /**
     * @param profile the profile to set
     */
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    //Beginning of signup page methods by Suguru
    public String freeSingup() {
        String retVal = null;
        ProfileDAO aProfileDAO = new ProfileDAO();
        profile.setPaid(false);
        int status = aProfileDAO.createUser(profile);
        if (status == 1) {
            retVal = "---.xhtml";
        } else {
            retVal = "error.xhtml";
        }
        return retVal;
    }
    
        public String paidSingup() {
        String retVal = null;
        ProfileDAO aProfileDAO = new ProfileDAO();
        profile.setPaid(true);
        int status = aProfileDAO.createUser(profile);
        if (status == 1) {
            retVal = "creditRegistration.xhtml";
        } else {
            retVal = "error.xhtml";
        }
        return retVal;
    }
        public String registerCreditCard() {
            String retVal = null;
            ProfileDAO aProfilDAO = new ProfileDAO();
            int status = aProfilDAO.addCreditCard(profile);
            if (status==1) {
                retVal = "-----.xhtml";
            } else {
                retVal = "error.xhtml";
            }
            
            return retVal;
        }
    //End of singup page methods by Suguru
        
    //For Dropdown menu
        
        
        
        
    // For Dropdown menu



}
