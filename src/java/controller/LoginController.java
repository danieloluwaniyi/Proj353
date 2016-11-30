/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProfileDAO;
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

    private boolean loggedIn = false;
    @ManagedProperty("#{profile}")
    private Profile profile;
    @ManagedProperty("#{profileDAO}")
    private ProfileDAO profileDAO;


    //To see if the user has already logged in
    public void checkIfLoggedIn() {
        if (!isLoggedIn()) {
            // Can't just return "login" as it not an "action" event (// Ref: http://stackoverflow.com/questions/16106418/how-to-perform-navigation-in-prerenderview-listener-method)
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("login?faces-redirect=true");
        }
    }

    //Login
    public String login() {
        String retVal = null;

        int status = getProfileDAO().login(profile);
        if (status == 1) {
            setLoggedIn(true);
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("dashboard?faces-redirect=true");
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
        return "index.xhtml?faces-redirect=true";
    }

    //Getters & setters
    /**
     * @return the loggedIn
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * @param loggedIn the loggedIn to set
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
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
    //Getters & setters

}