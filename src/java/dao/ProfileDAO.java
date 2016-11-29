package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Profile;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 * Reference: http://javaonlineguide.net/2016/01/how-to-display-images-in-datatable-using-pgraphicimage-in-primefaces.html
               https://www.mkyong.com/hibernate/hibernate-save-image-into-database/
               http://www.programcreek.com/2009/02/java-convert-image-to-byte-array-convert-byte-array-to-image/
 */
/**
 *
 * @author Suguru, Daniel, Sneh
 */
public class ProfileDAO {

    //Signup methods
    
    public boolean validateNewUser(Profile profile) {
        boolean retVal = false;
        
        retVal = this.CheckUserExists(profile.getUserID());
        retVal = this.checkEmailExists(profile.getEmail());
        retVal = this.checkPasswordMatch(profile.getPassword(), profile.getPasswordConf());
        retVal = this.checkPassword(profile.getPassword());
        retVal = this.checkEmailValidity(profile.getEmail());
        System.out.println("Is valid? " + retVal);
        
        return retVal;
    }
    
    
    
    //Checks if the userID & email are already exist.
    public boolean CheckUserExists(String userID) {
        boolean retVal = false;

        String userIDQuery = "SELECT * FROM project353.users WHERE user_id = ?";
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {
            PreparedStatement pstmt = DBConn.prepareStatement(userIDQuery);
            pstmt.setString(1, userID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                retVal = true;
            }
            DBConn.close();
            rs.close();
        } catch (SQLException ex) {
            System.err.println(ex + " was caught.");
        }

        return retVal;
    }
    
    //checks if the email already exists.
    public boolean checkEmailExists(String email) {
        boolean retVal = false;
        
        String emailQuery = "SELECT * FROM project353.users WHERE email = ?";
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        
        try {
            PreparedStatement pstmt = DBConn.prepareStatement(emailQuery);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                retVal = true;
            }
        } catch (SQLException ex) {
            System.err.println(ex + " was caught.");
        }
        return retVal;
    }

    public boolean checkEmailValidity(String email) {
        boolean retVal = true;

        //checks if @ is not at the beginning and end of the email.
        if ((email.indexOf("@") == 0) || (email.indexOf("@") == email.length())) {
            retVal = false;
        }

        //checks if email contains "@"
        if (email.indexOf("@") == -1) {
            retVal = false;
        }
        
        //checks if the email contains 2 @s. If so it'll return false
        int counter = 0;
        for (int i = 0; i < email.length() - 1; i++) {
            if (email.substring(i, i + 1).equals("@")) {
                counter++;
            }
        }
        if (counter > 1) {
            retVal = false;
        }

        return retVal;
    }
    
    public boolean checkPasswordMatch(String password, String passwordConf) {
        if (!password.equals(passwordConf)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkPassword(String password) {
        boolean retVal = false;

        String upperCharacters = "ABCDEFGHIJKLMNOPQRSTUWYZ";
        String lowerCharacters = "abcdefghijklmnopqrstuwyz";

        //Checks the length
        if (password.length() >= 6) {
            retVal = true;
        }

        //Checks if the email contains both upper and lower cases
        if (password.indexOf(upperCharacters) != -1) {
            retVal = true;
        } else if (password.indexOf(lowerCharacters) != -1) {
            retVal = true;
        }

        //Checking if the password contains special characters
        if ((password.indexOf("@") != -1) || (password.indexOf("#") != -1) || (password.indexOf("$") != -1) || (password.indexOf("%") != -1)) {
            retVal = true;
        }

        return retVal;
    }

    public int createUser(Profile profile) {
        int retVal = 0;

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        String myDB = "jdbc:derby://localhost:1527/project353";
        try {
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
            String insertString = "INSERT INTO User (firstName, lastName, userName, Email, Password, Paid) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = DBConn.prepareStatement(insertString);
            pstmt.setString(1, profile.getFirstName());
            pstmt.setString(2, profile.getLastName());
            pstmt.setString(3, profile.getUserID());
            pstmt.setString(4, profile.getEmail());
            pstmt.setString(5, profile.getPassword());
            pstmt.setBoolean(6, profile.getPaid());

            retVal = pstmt.executeUpdate(insertString);
            DBConn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retVal;
    }

    public int addCreditCard(Profile profile) {
        int retVal = 0;

        return retVal;
    }

    
    public boolean payUser(String userID) {
        boolean userPaid = false;

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        try {
            String myDB = "jdbc:derby://localhost:1527/project353";
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
            String query = "Update project353.submissions set paid =" + true + " where USER_ID=" + userID + ";";
            PreparedStatement stmt = DBConn.prepareStatement(query);
            userPaid = true;
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return userPaid;
    }

    public boolean payRoalty(String UserID) {
        boolean roaltyPaid = false;

        System.out.println("Roalty is paid to: " + UserID);
        roaltyPaid = true;

        return roaltyPaid;
    }

    public boolean addToCartDAO(String userID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}

