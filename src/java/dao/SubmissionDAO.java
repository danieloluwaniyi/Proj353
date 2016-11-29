/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Submission;

/**
 * * Reference: http://javaonlineguide.net/2016/01/how-to-display-images-in-datatable-using-pgraphicimage-in-primefaces.html
               https://www.mkyong.com/hibernate/hibernate-save-image-into-database/
               http://www.programcreek.com/2009/02/java-convert-image-to-byte-array-convert-byte-array-to-image/
 * @author Daniel
 */
public class SubmissionDAO {
    public byte[] getSubmissionContent(String subId) {

        int submissionId = Integer.parseInt(subId);

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

    public  void insertImage(byte[] file) {
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
//<<<<<<< HEAD

            // byte[] array = Files.readAllBytes(new File("/path/to/file").toPath());
//            Path path = Paths.get("I:\\NetBeansApps\\Proj353\\downloaded_optimus.jpg");
//            byte[] data = Files.readAllBytes(path);
//            ps.setBytes(3, data);
            
          
 //           byte[] data = Files.readAllBytes(new File("/Proj353/downloaded_optimus.jpg").toPath());
//            Path path = Paths.get("downloaded_optimus.jpg");
//            byte[] data = Files.readAllBytes(path);
            ps.setBytes(3, file);
//>>>>>>> origin/master
            ps.execute();
            ps.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public ArrayList findAllSubmissions() {
        ArrayList<Submission> submission = new ArrayList();
        String query = "select * from project353.submissions";
        submission = getAllSubmissions(query);
        return submission;

    }

    private ArrayList getAllSubmissions(String query) {
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
            double rating = 0.0;
            byte[] image = null;
            int id = 0;
            double price;
            while (rs.next()) {
                username = rs.getString("user_id");
                rating = rs.getDouble("rating");
                id = rs.getInt("submission_id");
                price = rs.getDouble("price");
                image = rs.getBytes("submission_content");

                submission = new Submission(rating, image, id, price);
                submissionCollection.add(submission);
            }

            rs.close();
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return submissionCollection;
    }

}
