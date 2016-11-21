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

/**
 *
 * @author Suguru, Daniel, Sneh
 */
@ManagedBean
@SessionScoped
public class ProfileController {

    @ManagedProperty("#{profile}")
    private Profile profile;
    @ManagedProperty("#{aProfileDAO}")
    private ProfileDAO aProfileDAO;
    private boolean userExists;
    private String errorMsg;

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
     * @return the aProfileDAO
     */
    public ProfileDAO getaProfileDAO() {
        if (aProfileDAO == null) {
            aProfileDAO = new ProfileDAO();
        }
        return aProfileDAO;
    }

    /**
     * @param aProfileDAO the aProfileDAO to set
     */
    public void setaProfileDAO(ProfileDAO aProfileDAO) {
        this.aProfileDAO = aProfileDAO;
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

    //Beginning of signup page methods by Suguru
    public void checkUserExistence() {
        if (getaProfileDAO().checkUserExists(profile.getUserID())) {
            this.setUserExists(true);
        } else {
            this.setUserExists(false);
            this.setErrorMsg("This user name is already taken. Please select a new one.");
        }
    }

    public String freeSingup() {
        String retVal = null;
        profile.setPaid(false);
        
        int status = getaProfileDAO().createUser(profile);

        if (status == 1) {
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("registrationConfirmation?faces-redirect=true");
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

        int status = getaProfileDAO().createUser(profile);

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
        ProfileDAO aProfilDAO = new ProfileDAO();
        int status = aProfilDAO.addCreditCard(profile);
        if (status == 1) {
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("registrationConfirmation?faces-redirect=true");
            retVal = "registrationConfirmation.xhtml";
        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("creditRegistration?faces-redirect=true");
//            retVal = "creditRegistration.xhtml";
        }

        return retVal;
    }
    //End of singup page methods by Suguru

    
}
