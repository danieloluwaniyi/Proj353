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
import java.io.Serializable;
import javax.faces.application.FacesMessage;

/**
 *
 * @author Suguru, Daniel, Sneh
 */
@ManagedBean
@SessionScoped
public class ProfileController implements Serializable {

    @ManagedProperty("#{profile}")
    private Profile profile;
    @ManagedProperty("#{profileDAO}")
    private ProfileDAO profileDAO;
    private boolean userExists;
    @ManagedProperty("#{email}")
    private Email email;
    private boolean mailed;
    private String errorMsg;
    private String updateMsg;
    
        //To see if the user has already logged in
    public void checkIfPaid() {
        if (!profile.getPaid()) {
            // Can't just return "login" as it not an "action" event (// Ref: http://stackoverflow.com/questions/16106418/how-to-perform-navigation-in-prerenderview-listener-method)
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("dashboard?faces-redirect=true");
        }
    }

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

    public void freeSingup() {
        profile.setPaid(false);

        int status = getProfileDAO().createUser(profile);

        if (status == 1) {
            setMailed(email.confirmationEmail(profile));
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            if (isMailed() == true) {
                nav.performNavigation("registrationConfirmation?faces-redirect=true");
            }
        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("freeSignup?faces-redirect=true");
        }
    }

    public void paidSingup() {
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
    }

    public void registerCreditCard() {
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
    }
    //End of singup page methods by Suguru
    
  //Updates
    public void updateName() {
        int status = 0;
            status = getProfileDAO().updateName(profile);
            this.setUpdateMsg("Name");
        if (status == 1) {
            setMailed(getEmail().updateEmail(profile, "name"));
            if (isMailed()) {
                FacesContext fc = FacesContext.getCurrentInstance();
                ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
                nav.performNavigation("updateConfirmation?faces-redirect=true");
            }
        } else {
            
        }
    }

    public void updateEmail() {
//        String retVal = null;
        int status = 0;
        if (profileDAO.checkPassMatch(profile)) {
            status = getProfileDAO().updateEmail(profile);
            this.setUpdateMsg("Email");
        } else {
            this.setErrorMsg("Password doesn't match to the user ID. Enter again");
        }
        if (status == 1) {
            setMailed(getEmail().updateEmail(profile, "email"));
            if (isMailed()) {
                FacesContext fc = FacesContext.getCurrentInstance();
                ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
                nav.performNavigation("updateConfirmation?faces-redirect=true");
            }
        }
    }

    public void updatePassword() {
        int status = 0;
            status = getProfileDAO().updatePassword(profile);
            this.setUpdateMsg("Password");

        if (status == 1) {
            setMailed(getEmail().updateEmail(profile, "password"));
            if (isMailed()) {
                FacesContext fc = FacesContext.getCurrentInstance();
                ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
                nav.performNavigation("updateConfirmation?faces-redirect=true");
            }
        } else {
            
        }
    }

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

    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg the errorMsg to set
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * @return the updateMsg
     */
    public String getUpdateMsg() {
        return updateMsg;
    }

    /**
     * @param updateMsg the updateMsg to set
     */
    public void setUpdateMsg(String updateMsg) {
        this.updateMsg = updateMsg;
    }

}