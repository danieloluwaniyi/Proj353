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
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Submission;

/**
 * * Reference:
 * http://javaonlineguide.net/2016/01/how-to-display-images-in-datatable-using-pgraphicimage-in-primefaces.html
 * https://www.mkyong.com/hibernate/hibernate-save-image-into-database/
 * http://www.programcreek.com/2009/02/java-convert-image-to-byte-array-convert-byte-array-to-image/
 *
 * @author Daniel
 */
@ManagedBean
@ApplicationScoped
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

    //Insert an image into the Submission Database
    public void insertImage(byte[] file, String username, String tags) {
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
            if ((tags == null) || (tags.equals(""))) {
                //do something
                tags = "None";
            }
            ps = DBConn.prepareStatement("insert into project353.submissions(USER_ID, RATING, SUBMISSION_CONTENT,TAGS) " + "values(?,?,?,?)");
            ps.setString(1, username);
            ps.setDouble(2, 0.0);

            ps.setBytes(3, file);
            ps.setString(4, tags);
            ps.execute();
            ps.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public ArrayList findAllSubmissions(int param, String tag) {
        ArrayList<Submission> submission = new ArrayList();
        String query = "select * from project353.submissions";
        if (param == 1) {
            query = "select * from project353.submissions";
        }
        if (param == 2) {//tags
            query = "select * from project353.submissions where tags like ?";
        }
        submission = getAllSubmissions(query, param, tag);
        return submission;

    }

    //Helper function for findAllSubmissions to get all the current submissions
    private ArrayList getAllSubmissions(String query, int param, String tag) {
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
            if (param == 2) {
                stmt.setString(1, "%" + tag + "%");
            }

            ResultSet rs = stmt.executeQuery();
            String username, tags;
            double rating = 0.0;
            byte[] image = null;
            int id = 0;
            int raters = 0;
            double price;
            while (rs.next()) {
                username = rs.getString("user_id");
                rating = rs.getDouble("rating");
                id = rs.getInt("submission_id");
                price = rs.getDouble("price");
                raters = rs.getInt("raters");
                image = rs.getBytes("submission_content");
                tags = rs.getString("tags");

                submission = new Submission(rating, image, id, price, raters, tags);
                submissionCollection.add(submission);
            }

            rs.close();
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return submissionCollection;
    }

    public int updateRating(int id, double rating, int raters) {
        PreparedStatement ps;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/project353";
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
            String query = "update project353.submissions set rating = ?, raters =?";
            query += "where submission_id = ?";
            ps = DBConn.prepareStatement(query);
            ps.setDouble(1, rating);
            ps.setInt(2, raters);
            ps.setInt(3, id);
            rowCount = ps.executeUpdate();

            ps.close();
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return rowCount;
    }
}
