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

    public boolean Email(Profile SignUpetails) {
        boolean sent = false;
        // Recipient's email ID needs to be mentioned.
        String to = SignUpetails.getEmail();

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
            message.setContent("Hi " + SignUpetails.getFirstName() + "," + "<br/>" + "You have won the contest based on user ratings." + "<br/>",
                    "text/html");

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

    public boolean confirmationEmail(Profile profile) {

        boolean sent = false;
        // Recipient's email ID needs to be mentioned.
        String to = profile.getEmail();

        // Sender's email ID needs to be mentioned
        String from = "suguru.tokuda@gmail.com";

        // Assuming you are sending email from this host
        String host = "smtp.gmail.com";

        final String username = "suguru.tokuda@gmail.com";
        final String password = "sfst0812";

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
            message.setSubject("ccount Created");
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
        String from = "suguru.tokuda@gmail.com";

        // Assuming you are sending email from this host
        String host = "smtp.gmail.com";

        final String username = "suguru.tokuda@gmail.com";
        final String password = "sfst0812";

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
                message.setSubject("Name updated");
                // Send the actual HTML message, as big as you like
                message.setContent("Your name has been updated!" + "<br/><br/>"
                        + "First name: " + profile.getFirstName() + "<br/>"
                        + "Last name: " + profile.getLastName() + "<br/>"
                        + "<br/>" + "Please keep in touch." + "<br/>" + "Regards," + "<br/>" + "Team Project353"
                        + "<br/>" + "<img src=\"http://content.sportslogos.net/logos/32/707/thumbs/wgpjcd57fikjji1qy97f2gsqk.gif\">",
                        "text/html");
            } else if (type.equals("email")) {
                // Set Subject: header field
                message.setSubject("Email updated");
                // Send the actual HTML message, as big as you like
                message.setContent("Your email has been updated!" + "<br/><br/>"
                        + "New email: " + profile.getEmail() + "<br/>"
                        + "<br/>" + "Please keep in touch." + "<br/>" + "Regards," + "<br/>" + "Team Project353"
                        + "<br/>" + "<img src=\"http://content.sportslogos.net/logos/32/707/thumbs/wgpjcd57fikjji1qy97f2gsqk.gif\">",
                        "text/html");
            } else if (type.equals("password")) {
                // Set Subject: header field
                message.setSubject("Password updated");
                // Send the actual HTML message, as big as you like
                message.setContent("Your password has been updated!" + "<br/><br/>"
                        + "New email: " + profile.getPassword() + "<br/>"
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

}