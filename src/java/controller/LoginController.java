/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProfileDAO;
import email.Email;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Profile;

/**
 *
 * @author Suguru
 */
@ManagedBean
@SessionScoped
public class LoginController {

    @ManagedProperty("#{profile}")
    private Profile profile;
    @ManagedProperty("#{profileDAO}")
    private ProfileDAO profileDAO;
    @ManagedProperty("#{email}")
    private Email email;
    private boolean mailed;
    private String errorMsg;
    private String updateMsg;

    //To see if the user has already logged in
    public void checkIfLoggedIn() {
        if (!profile.isLoggedIn()) {
            // Can't just return "login" as it not an "action" event (// Ref: http://stackoverflow.com/questions/16106418/how-to-perform-navigation-in-prerenderview-listener-method)
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("home?faces-redirect=true");
        }
    }

    //Login
    public String login() {
        String retVal = null;

        int status = getProfileDAO().login(profile);
        if (status == 1) {
            profile.setLoggedIn(true);
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("userIndex?faces-redirect=true");
        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("login?faces-redirect=true");
        }
        return retVal;
    }

    //Logout
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession(); // the above is unnecessary once the session is invalidated
        return "home?faces-redirect=true";
    }

    //Retreieve password
    public void retrievePassword() {
//        String retVal = null;
        Profile retProfile = null;
        retProfile = profileDAO.retrievePassword(profile.getEmail());

        if (retProfile != null) {
            this.setMailed(this.email.resendPassword(retProfile));
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            if (isMailed() == true) {
                nav.performNavigation("passwordResent?faces-redirect=true");
            }
        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            if (isMailed() == false) {
                nav.performNavigation("passwordResent?faces-redirect=true");
            }
        }
//        return retVal;
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
    //Getters & setters

    /**
     * @return the email
     */
    public Email getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(Email email) {
        this.email = email;
    }

    //TestTestTeset
    //Updates
    public String updateName() {
        String retVal = null;
        int status = 0;
        if (profileDAO.checkPassMatch(profile)) {
            status = getProfileDAO().updateName(profile);
            this.setUpdateMsg("Name");
        } else {
            this.setErrorMsg("Password doesn't match to the user ID. Enter again");
            return retVal;
        }
        if (status == 1) {
            setMailed(getEmail().updateEmail(profile, "name"));
            if (isMailed()) {
                FacesContext fc = FacesContext.getCurrentInstance();
                ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
                nav.performNavigation("updateConfirmation?faces-redirect=true");
            }
        }
        return retVal;
    }

    public String updateEmail() {
        String retVal = null;
        int status = 0;
        if (profileDAO.checkPassMatch(profile)) {
            status = getProfileDAO().updateEmail(profile);
            this.setUpdateMsg("Email");
        } else {
            this.setErrorMsg("Password doesn't match to the user ID. Enter again");
            return retVal;
        }
        if (status == 1) {
            setMailed(getEmail().updateEmail(profile, "email"));
            if (isMailed()) {
                FacesContext fc = FacesContext.getCurrentInstance();
                ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
                nav.performNavigation("updateConfirmation?faces-redirect=true");
            }
        }

        return retVal;
    }

    public void updatePassword() {
        String retVal = null;
        int status = 0;
//        if (profileDAO.checkPassMatch(profile)) {
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

//        return retVal;
    }
    //TestTestTest

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

    //TestTestTest
}