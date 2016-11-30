/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProfileDAO;
import java.util.Properties;
import javax.faces.application.ConfigurableNavigationHandler;
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
public class AdminController {
     private Submission submissionModel;
     private Profile profile;
     private ProfileDAO profiledao;
     private Admin admin;

     
     public boolean email(Profile profile) {
        boolean sent = false;
        // Recipient's email ID needs to be mentioned.
        String to = profile.getEmail();

        // Sender's email ID needs to be mentioned
        String from = "svyas12@ilstu.edu";

        // Assuming you are sending email from this host
        String host = "m.outlook.com";

        // Get system properties
        Properties props = System.getProperties();

        // Setup mail server
        props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587"); // if needed
        props.put("mail.smtp.host", "m.outlook.com"); // if needed
        props.put("mail.smtp.auth", "true");
        // Get the default Session object.
        Session session = Session.getDefaultInstance(props);
        session = Session.getInstance(props, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("svyas12@ilstu.edu",
                    "Sohanlal1!");
        }
    });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Your Account has been created");
            // Send the actual HTML message, as big as you like
            message.setContent("Hi " + profile.getFirstName() + "," + "<br/>" + "You have won the contest based on user ratings." + "<br/>"
                                 ,"text/html");

            // Send message
            Transport.send(message);
            sent = true;
            System.out.println("Message sent successfully....");

        } catch (MessagingException mex) {
            mex.printStackTrace();
            sent = false;
        }
        return sent;

    }
     
    public boolean pay(Profile profile){
        boolean isUsergetPaid = false;
        String userID = profile.getUserID();
        if(profiledao.payUser(userID)){
            isUsergetPaid = true;
        }
        return isUsergetPaid;
    } 
    
    public boolean payRoalty(Profile profile){
        boolean roalty = false;
        String userID  = profile.getUserID();
        if(profiledao.payRoalty(userID)){
            roalty = true;
        }
        return roalty;
    }
    
//    public String loginAdmin(){
//        if(admin.getAdminPass()=="admin"&&admin.getAdminUname()=="admin"){
//            return "login.xhtml";
//        }
//        else{
//          return "login.xhtml";
//        }
//    }
    
    
    
    public String Adminlogin() {
        String retVal = null;

        
        FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("login?faces-redirect=true");
        
//        if (admin.getAdminPass()=="admin"&&admin.getAdminUname()=="admin") {
//            
//            FacesContext fc = FacesContext.getCurrentInstance();
//            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
//            nav.performNavigation("login?faces-redirect=true");
//        } else {
//            FacesContext fc = FacesContext.getCurrentInstance();
//            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
//            nav.performNavigation("login?faces-redirect=true");
//        }
        return retVal;
    }
        
        
    }

    