/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProfileDAO;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.SessionScoped;
import model.Profile;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import email.Email;

/**
 *
 * @author Suguru, Daniel, Sneh
 */
@ManagedBean
@SessionScoped
public class ProfileController {

    @ManagedProperty("#{profile}")
    private Profile profile;
    @ManagedProperty("#{profileDAO}")
    private ProfileDAO profileDAO;
    private boolean userExists;
    @ManagedProperty("#{email}")
    private Email email;
    private boolean mailed;


    /**
     * @return the profile
     */
    public Profile getProfile() {
        if (profile == null) {
            profile = new Profile();
        }
        return profile;
    }

    /**
     * @param profile the profile to set
     */
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    /**
     * @return the profileDAO
     */
    public ProfileDAO getProfileDAO() {
        if (profileDAO == null) {
            profileDAO = new ProfileDAO();
        }
        return profileDAO;
    }

    /**
     * @param profileDAO the profileDAO to set
     */
    public void setProfileDAO(ProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
    }

    /**
     * @return the email
     */
    public Email getEmail() {
//        if (email == null) {
//            email = new Email();
//        }
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(Email email) {
        this.email = email;
    }

    /**
     * @return the userExists
     */
    public boolean isUserExists() {
        return userExists;
    }

    /**
     * @param userExists the userExists to set
     */
    public void setUserExists(boolean userExists) {
        this.userExists = userExists;
    }



    //Beginning of signup page methods by Suguru
    public void checkUserExistence() {
        if (getProfileDAO().checkUserExists(profile.getUserID())) {
            this.setUserExists(true);
        } else {
            this.setUserExists(false);
        }
    }

    public String freeSingup() {
        String retVal = null;
        profile.setPaid(false);

        int status = getProfileDAO().createUser(profile);

        if (status == 1) {
            setMailed(email.confirmationEmail(profile));
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            if (isMailed() == true) {
                nav.performNavigation("registrationConfirmation?faces-redirect=true");
            }
//            retVal = "registrationConfirmation.xhtml";
        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("freeSignup?faces-redirect=true");
//            retVal = "error.xhtml";
        }
        return retVal;
    }

    public String paidSingup() {
        String retVal = null;
        profile.setPaid(true);

        int status = getProfileDAO().createUser(profile);

        if (status == 1) {
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("creditRegistration?faces-redirect=true");
//            retVal = "creditRegistration.xhtml";
        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("paidSignup?faces-redirect=true");
//            retVal = "error.xhtml";
        }
        return retVal;
    }

    public String registerCreditCard() {
        String retVal = null;

        int status = getProfileDAO().addCreditCard(profile);
        if (status == 1) {
            setMailed(this.getEmail().confirmationEmail(profile));
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("registrationConfirmation?faces-redirect=true");
//            retVal = "registrationConfirmation.xhtml";
        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("creditRegistration?faces-redirect=true");
//            retVal = "creditRegistration.xhtml";
        }

        return retVal;
    }
    //End of singup page methods by Suguru

    

    /**
     * @return the mailed
     */
    public boolean isMailed() {
        return mailed;
    }

    /**
     * @param mailed the mailed to set
     */
    public void setMailed(boolean mailed) {
        this.mailed = mailed;
    }

}