package dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Profile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import model.Submission;

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
    public boolean CheckUserExists(Profile profile) {
        boolean retVal = false;
        
        String query = "SELECT * FROM user WHERE userid = ?";
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        
        try {
            PreparedStatement pstmt = DBConn.prepareStatement(query);
            pstmt.setString(1, profile.getUserID());
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
    
    
    

    public byte[] getSubmissionContent(String subId) {
        
        int submissionId=Integer.parseInt(subId);

        byte[] submission = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        try {
            String myDB = "jdbc:derby://localhost:1527/project353";
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            stmt = DBConn.prepareStatement("select * from project353.submissions where submission_Id=?");
            stmt.setInt(1, submissionId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                submission = rs.getBytes("submission_content");
            }

            rs.close();
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return submission;
    }

    public  void insertImage() {
        PreparedStatement ps;

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        try {
            String myDB = "jdbc:derby://localhost:1527/project353";
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            ps = DBConn.prepareStatement("insert into project353.submissions(USER_ID, RATING, SUBMISSION_CONTENT) " + "values(?,?,?)");
            ps.setString(1, "doluwan");
            ps.setDouble(2, 5.0);

           // byte[] array = Files.readAllBytes(new File("/path/to/file").toPath());
            Path path = Paths.get("I:\\NetBeansApps\\Proj353\\downloaded_optimus.jpg");
            byte[] data = Files.readAllBytes(path);
            ps.setBytes(3, data);
            ps.execute();
            ps.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
                    
        }

    }
    
    public ArrayList findAllSubmissions(){
        ArrayList<Submission> submission = new ArrayList();
        String query = "select * from project353.submissions";
        submission = getAllSubmissions(query);
        return submission;
        
        
    }
    
    
    
    
    private ArrayList getAllSubmissions(String query){
        ArrayList<Submission> submissionCollection = new ArrayList();
        Submission submission = null;

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        try {
            String myDB = "jdbc:derby://localhost:1527/project353";
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

             PreparedStatement stmt = DBConn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery();
             String username;
             double rating=0.0;
             byte[] image=null;
             int id=0;
            while (rs.next()) {
                username = rs.getString("user_id");
                rating = rs.getDouble("rating");
                id= rs.getInt("submission_id");
                image = rs.getBytes("submission_content");
                
            submission = new Submission(rating,image,id);
            submissionCollection.add(submission);
           }

            
            rs.close();
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
        return submissionCollection;
    }
    
    
    
    
    
    
    public boolean payUser(String userID){
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
             String query = "Update project353.submissions set paid =" +true+" where USER_ID="+userID+";";
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
        
        System.out.println("Roalty is paid to: "+UserID);
        roaltyPaid = true;
        
        
        return roaltyPaid;
    }

    public boolean addToCartDAO(String userID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    
}
