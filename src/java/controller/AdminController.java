/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProfileDAO;
import dao.RoyaltyDAO;
import dao.WinnerDAO;
import email.Email;
import java.util.Properties;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.Profile;
import model.Submission;
import model.Admin;
/**
 *
 * @author vyass
 */
@ManagedBean
@SessionScoped
public class AdminController {
     private Submission submissionModel;
     @ManagedProperty("#{profile}")
     private Profile profile;
     @ManagedProperty("#{profileDAO}")
     private ProfileDAO profileDAO;
     @ManagedProperty("#{royaltyDAO}")
     private RoyaltyDAO royaltyDAO;
     @ManagedProperty("#{winnerDAO}")
     private WinnerDAO winnerDAO;
     @ManagedProperty("#{admin}")
     private Admin admin;
     private Email emailToSent;
     private String userIDToPay;
     private String errorMsg;
     private boolean loggedInAsAdmin;
     
     public void checkIfLoggedInAsAdmin() {
        if (!loggedInAsAdmin) {
            // Can't just return "login" as it not an "action" event (// Ref: http://stackoverflow.com/questions/16106418/how-to-perform-navigation-in-prerenderview-listener-method)
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("adminLogin?faces-redirect=true");
        }
    }
    public void logoutAdmin() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession(); // the above is unnecessary once the session is invalidated
        FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            loggedInAsAdmin = false;
            nav.performNavigation("adminLogin?faces-redirect=true");
    }
    public void loginAdmin() {
//        String retVal = null;
           String adminpass = admin.getAdminPass();
           String adminuname = admin.getAdminUname();
           loggedInAsAdmin = true;

        if (adminuname.equals("admin")&&  adminpass.equals("admin")) {
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("adminMenu?faces-redirect=true");
        } 
        else {
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("adminLoginFail?faces-redirect=true");
        }
//        return retVal;
    }
        
    public void payRoyalty() {
        boolean status = false;
        

        status = royaltyDAO.payRoyalty();
        
        if (status == true) {
        FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("royaltyPayConf?faces-redirect=true");
        } else {
            this.errorMsg = "Error, please check the data.";
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("payroyalties?faces-redirect=true");
        }
    }

    public void payWinner() {
        boolean status = false;
        

        status = getWinnerDAO().payWinners();
        
        if (status == true) {
        FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("winnerPayConf?faces-redirect=true");
        } else {
            this.errorMsg = "Error, please check the data.";
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("paywinner?faces-redirect=true");
        }
    }
    

    public Submission getSubmissionModel() {
        return submissionModel;
    }

    public void setSubmissionModel(Submission submissionModel) {
        this.submissionModel = submissionModel;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public ProfileDAO getProfileDAO() {
        return profileDAO;
    }


    public void setProfileDAO(ProfileDAO profiledao) {
        this.profileDAO = profiledao;
    }


    public Admin getAdmin() {
        if(admin == null)
            admin = new Admin();
       return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getUserIDToPay() {
        return userIDToPay;
    }

    public void setUserIDToPay(String userIDToPay) {
        this.userIDToPay = userIDToPay;
    }

    /**
     * @return the emailToSent
     */
    public Email getEmailToSent() {
        return emailToSent;
    }

    /**
     * @param emailToSent the emailToSent to set
     */
    public void setEmailToSent(Email emailToSent) {
        this.emailToSent = emailToSent;
    }

    /**
     * @return the royaltyDAO
     */
    public RoyaltyDAO getRoyaltyDAO() {
        return royaltyDAO;
    }

    /**
     * @param royaltyDAO the royaltyDAO to set
     */
    public void setRoyaltyDAO(RoyaltyDAO royaltyDAO) {
        this.royaltyDAO = royaltyDAO;
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
     * @return the loggedInAsAdmin
     */
    public boolean isLoggedInAsAdmin() {
        return loggedInAsAdmin;
    }

    /**
     * @param loggedInAsAdmin the loggedInAsAdmin to set
     */
    public void setLoggedInAsAdmin(boolean loggedInAsAdmin) {
        this.loggedInAsAdmin = loggedInAsAdmin;
    }

    /**
     * @return the winnerDAO
     */
    public WinnerDAO getWinnerDAO() {
        return winnerDAO;
    }

    /**
     * @param winnerDAO the winnerDAO to set
     */
    public void setWinnerDAO(WinnerDAO winnerDAO) {
        this.winnerDAO = winnerDAO;
    }
}