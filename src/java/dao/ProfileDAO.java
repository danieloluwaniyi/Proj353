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
 */

/**
 *
 * @author Suguru, Daniel, Sneh
 */
public class ProfileDAO {
    
    
    //Signup methods
    public boolean CheckUserExists(Profile profile) {
        boolean retVal = false;
        
        String query = "SELECT * FROM user WHERE userid = ?";
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/profile";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        
        try {
            PreparedStatement pstmt = DBConn.prepareStatement(query);
            pstmt.setString(1, profile.getUserName());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                retVal = true;
            }
            DBConn.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retVal;
    }
    
    public int createUser(Profile profile)  {
        int retVal = 0;
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        
        String myDB = "jdbc:derby://localhost:1527/profile";
        try {
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
            String insertString = "INSERT INTO User (firstName, lastName, userName, Email, Password, Paid) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = DBConn.prepareStatement(insertString);
            pstmt.setString(1, profile.getFirstName());
            pstmt.setString(2, profile.getLastName());
            pstmt.setString(3, profile.getUserName());
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
    
    
    
}
