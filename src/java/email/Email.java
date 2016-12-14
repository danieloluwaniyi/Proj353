/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import java.io.Serializable;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.Profile;

@ManagedBean
@SessionScoped
public class Email implements Serializable {
       
        public void winnerEmail(String email) {
        String to = email;
        String username = "ccola2017@gmail.com";
        String password = "Ccola2017!";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ccola2017@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Congratulations...");
            message.setText("You have won the weekly contest and had been paid the prize money.");
                    

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
        
        
    public void royaltyEmail(String email) {
        String to = email;
        String username = "ccola2017@gmail.com";
        String password = "Ccola2017!";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ccola2017@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Congratulations...");
            message.setText("You have been paid Royalty for your painting.");
                    

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean confirmationEmail(Profile profile) {

        boolean sent = false;
        // Recipient's email ID needs to be mentioned.
        String to = profile.getEmail();

        // Sender's email ID needs to be mentioned
        String from = "ccola2017@gmail.com";

        // Assuming you are sending email from this host
        String host = "smtp.gmail.com";

        final String username = "ccola2017@gmail.com";
        final String password = "Ccola2017!";

        // Get system properties
        Properties props = System.getProperties();

        // Setup mail server
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587"); // if needed
        props.put("mail.smtp.host", "smtp.gmail.com"); // if needed

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
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
            message.setSubject("Submissions Portal: Account Created");
            // Send the actual HTML message, as big as you like
            message.setContent("Hi " + profile.getFirstName() + "," + "<br/>" + "You have been Succesfully signed up with Submissions Portal!" + "<br/><br/>"
                    + "Your UserID :" + profile.getUserID() + "<br/>"
                    + "<br/>" + "Please keep in touch." + "<br/>" + "Regards," + "<br/>" + "Team Project353"
                    + "<br/>" + "<img src=\"http://content.sportslogos.net/logos/32/707/thumbs/wgpjcd57fikjji1qy97f2gsqk.gif\">",
                    "text/html");

            // Send message
            Transport.send(message);
            sent = true;
            System.out.println("Sent message successfully....");

        } catch (MessagingException mex) {
            mex.printStackTrace();
            sent = false;
        }
        return sent;
    }

    public boolean updateEmail(Profile profile, String type) {
        boolean sent = false;
        // Recipient's email ID needs to be mentioned.
        String to = profile.getEmail();

        // Sender's email ID needs to be mentioned
        String from = "ccola2017@gmail.com";

        // Assuming you are sending email from this host
        String host = "smtp.gmail.com";

        final String username = "ccola2017@gmail.com";
        final String password = "Ccola2017!";

        // Get system properties
        Properties props = System.getProperties();

        // Setup mail server
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587"); // if needed
        props.put("mail.smtp.host", "smtp.gmail.com"); // if needed

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
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

            if (type.equals("name")) {
                // Set Subject: header field
                message.setSubject("Submissions Portal: Name updated");
                // Send the actual HTML message, as big as you like
                message.setContent("Your name has been updated!" + "<br/><br/>"
                        + "First name: " + profile.getFirstName() + "<br/>"
                        + "Last name: " + profile.getLastName() + "<br/>"
                        + "<br/>" + "Please keep in touch." + "<br/>" + "Regards," + "<br/>" + "Team Project353"
                        + "<br/>" + "<img src=\"http://content.sportslogos.net/logos/32/707/thumbs/wgpjcd57fikjji1qy97f2gsqk.gif\">",
                        "text/html");
            } else if (type.equals("email")) {
                // Set Subject: header field
                message.setSubject("Submissions Portal: Email updated");
                // Send the actual HTML message, as big as you like
                message.setContent("Your email has been updated!" + "<br/><br/>"
                        + "New email: " + profile.getEmail() + "<br/>"
                        + "<br/>" + "Please keep in touch." + "<br/>" + "Regards," + "<br/>" + "Team Project353"
                        + "<br/>" + "<img src=\"http://content.sportslogos.net/logos/32/707/thumbs/wgpjcd57fikjji1qy97f2gsqk.gif\">",
                        "text/html");
            } else if (type.equals("password")) {
                // Set Subject: header field
                message.setSubject("Submissions Portal: Password updated");
                // Send the actual HTML message, as big as you like
                message.setContent("Your password has been updated!" + "<br/><br/>"
                        + "New password: " + profile.getPassword() + "<br/>"
                        + "<br/>" + "Please keep in touch." + "<br/>" + "Regards," + "<br/>" + "Team Project353"
                        + "<br/>" + "<img src=\"http://content.sportslogos.net/logos/32/707/thumbs/wgpjcd57fikjji1qy97f2gsqk.gif\">",
                        "text/html");
            }

            // Send message
            Transport.send(message);
            sent = true;
            System.out.println("Sent message successfully....");

        } catch (MessagingException mex) {
            mex.printStackTrace();
            sent = false;
        }
        return sent;
    }
    
    //Resend password to the user
    public boolean resendPassword(Profile profile) {
        boolean sent = false;
        // Recipient's email ID needs to be mentioned.
        String to = profile.getEmail();

        // Sender's email ID needs to be mentioned
        String from = "ccola2017@gmail.com";

        // Assuming you are sending email from this host
        String host = "smtp.gmail.com";

        final String username = "ccola2017@gmail.com";
        final String password = "Ccola2017!";

        // Get system properties
        Properties props = System.getProperties();

        // Setup mail server
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587"); // if needed
        props.put("mail.smtp.host", "smtp.gmail.com"); // if needed

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
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
                message.setSubject("Submissions Portal: Password resent");
                // Send the actual HTML message, as big as you like
                message.setContent("Here's your password!" + "<br/><br/>"
                        + "User ID: " + profile.getUserID() + "<br/>"
                        + "Password: " + profile.getPassword() + "<br/>"
                        + "<br/>" + "Please keep in touch." + "<br/>" + "Regards," + "<br/>" + "Team Project353"
                        + "<br/>" + "<img src=\"http://content.sportslogos.net/logos/32/707/thumbs/wgpjcd57fikjji1qy97f2gsqk.gif\">",
                        "text/html");

            // Send message
            Transport.send(message);
            sent = true;
            System.out.println("Sent message successfully....");

        } catch (MessagingException mex) {
            mex.printStackTrace();
            sent = false;
        }
        return sent;
    }

}
