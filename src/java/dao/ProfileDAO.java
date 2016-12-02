package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    //Checks if the userID & email are already exist.
    public boolean checkUserExists(String userID) {
        boolean retVal = false;

        String userIDQuery = "SELECT * FROM project353.users WHERE user_id = ?";
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {
            PreparedStatement pstmt = DBConn.prepareStatement(userIDQuery);
            pstmt.setString(1, userID.toLowerCase());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String userIDFromDB = rs.getString("user_id");
                if (userIDFromDB.toLowerCase().equals(userID.toLowerCase())) {
                    retVal = true;
                }
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
                String emailFromDB = rs.getString("email");
                if (emailFromDB.toLowerCase().equals(email.toLowerCase())) {
                    retVal = true;
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex + " was caught.");
        }
        return retVal;
    }

    //returns true if email is good
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

    //returns true if both password and input match.
    public boolean checkPasswordMatch(String password, String passwordConf) {
        if (!password.equals(passwordConf)) {
            return false;
        } else {
            return true;
        }
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
            String insertString = "INSERT INTO Project353.Users (firstName, lastName, user_ID, Email, Password, Paid) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = DBConn.prepareStatement(insertString);
            pstmt.setString(1, profile.getFirstName());
            pstmt.setString(2, profile.getLastName());
            pstmt.setString(3, profile.getUserID().toLowerCase());
            pstmt.setString(4, profile.getEmail().toLowerCase());
            pstmt.setString(5, profile.getPassword());
            pstmt.setBoolean(6, profile.getPaid());

            pstmt.execute();
            retVal = 1;
            DBConn.close();
        } catch (SQLException ex) {
            System.out.println(ex + " was caught.");
        }
        return retVal;
    }

    public int addCreditCard(Profile profile) {
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
            String insertString = "UPDATE PROJECT353.USERS SET nameOnCard = ?, creditCardNum = ?, securityCode = ?, expirationMonth = ?, expirationYear = ? WHERE user_id = ?";
            PreparedStatement pstmt = DBConn.prepareStatement(insertString);
            pstmt.setString(1, profile.getNameOnCard().toUpperCase());
            pstmt.setString(2, profile.getCreditCardNum());
            pstmt.setString(3, profile.getSecurityCode());
            pstmt.setInt(4, profile.getExpirationMonth());
            pstmt.setInt(5, profile.getExpirationYear());
            pstmt.setString(6, profile.getUserID().toLowerCase());

            pstmt.execute();
            retVal = 1;
            DBConn.close();
        } catch (SQLException ex) {
            System.out.println(ex + " was caught.");
        }
        return retVal;
    }
    
    public int login(Profile profile) {
        int retVal = 0;
        boolean logInGood = false;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        String myDB = "jdbc:derby://localhost:1527/project353";
        try {
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
            String idQuery = "SELECT user_ID FROM project353.users WHERE user_ID = ?";
            PreparedStatement pstmtForID = DBConn.prepareStatement(idQuery);
            pstmtForID.setString(1, profile.getUserID().toLowerCase());
            
            ResultSet rs = pstmtForID.executeQuery();
            while (rs.next()) {
                String id = rs.getString("user_ID");
                if (id.toLowerCase().equals(profile.getUserID().toLowerCase())) {
                    logInGood = true;
                }
            }
            
            String passQuery = "SELECT password FROM project353.users WHERE password = ?";
            PreparedStatement pstmtForPass = DBConn.prepareStatement(passQuery);
            pstmtForPass.setString(1, profile.getPassword());
            rs = pstmtForPass.executeQuery();
            while(rs.next()) {
                String pass = rs.getString("password");
                if (pass.equals(profile.getPassword())) {
                    logInGood = true;
                } else {
                    logInGood = false;
                }
            }
            if (logInGood) {
                retVal = 1;
            }        
            DBConn.close();
        } catch (SQLException ex) {
            System.out.println(ex + " was caught.");
        }

        return retVal;
    }

    public int update(Profile profile) {

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
            String insertString = "UPDATE Project353.Users SET firstName=?, lastName=?, email=?, password=? WHERE user_id = ?";
            PreparedStatement pstmt = DBConn.prepareStatement(insertString);
            pstmt.setString(1, profile.getFirstName());
            pstmt.setString(2, profile.getLastName());
            pstmt.setString(3, profile.getEmail());
            pstmt.setString(4, profile.getPassword());
            pstmt.setString(5, profile.getUserID());
            pstmt.execute();
            retVal = 1;
            DBConn.close();
        } catch (SQLException ex) {
            System.out.println(ex + " was caught.");
        }
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
            stmt.execute();
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
//    public boolean addToCartDAO(String userID) {
//
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    
//    
//
//}
//<<<<<<< HEAD
////<<<<<<< HEAD
////        } catch (IOException ex) {
////            System.out.println(ex.getMessage());
////
////
////>>>>>>> origin/master
//


