/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import email.Email;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Royalty;
import model.Winner;

/**
 *
 * @author vyass
 */
@ManagedBean
@SessionScoped
public class WinnerBean implements Serializable {

    private List<String> emails = new ArrayList<String>();
    private Email email = new Email();

    public double selectWinner(Date date) {
        System.out.println(date);
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        double winner_id  =0;
        try {
            String Query = "SELECT submission_id FROM Project353.submissions where {fn TIMESTAMPDIFF( SQL_TSI_DAY, ?, CURRENT DATE)}>=7 and rating= (select max(rating) from Project353.submissions) and WINNER = FALSE";
            PreparedStatement s = DBConn.prepareStatement(Query);
            s.setDate(1, date);
            ResultSet rs = s.executeQuery();
            Statement s1 = DBConn.createStatement();
          
            while (rs.next()) {
                 winner_id = rs.getDouble("Submission_id");
                String updateQuery = "UPDATE PROJECT353.SUBMISSIONS SET WINNER = TRUE WHERE SUBMISSION_ID = " + winner_id;
                int i = s1.executeUpdate(updateQuery);
            }
        } catch (SQLException se) {
            System.out.println("======");
            System.out.println("======");
            System.out.println("======");
            se.printStackTrace();
            System.out.println("======");
            System.out.println("======");
            System.out.println("======");
        }
        return winner_id;
    }

    public String setWinners() {
      
        PreparedStatement ps;
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
         Date date = null;
        try {
            String dateQuery = "SELECT max(windate) WINDATE FROM Project353.winnner ";
           
           
            Statement s = DBConn.createStatement();
            ResultSet rs;
            rs = s.executeQuery(dateQuery);
            while(rs.next()){
                date=rs.getDate("WINDATE");
            }
            double id = selectWinner(date);
            
            String Query = "SELECT * FROM Project353.submissions where submission_id= "+id;
            rs = s.executeQuery(Query);
//            Statement s1 = DBConn.createStatement();
            while (rs.next()) {
                double winner_id = rs.getDouble("Submission_id");
                String user_id = rs.getString("USER_ID");
//                String updateQuery = "UPDATE PROJECT353.SUBMISSIONS SET WINNER = TRUE WHERE SUBMISSION_ID = " + winner_id;
//                int i = s1.executeUpdate(updateQuery);
                ps = DBConn.prepareStatement("insert into project353.winnner (USER_ID,SUBMISSION_ID) VALUES(?,?)");
                ps.setString(1, user_id);
                ps.setDouble(2,winner_id);
                ps.execute();
                ps.close();
            }
        } catch (SQLException se) {
            System.out.println("======");
            System.out.println("======");
            System.out.println("======");
            se.printStackTrace();
            System.out.println("======");
            System.out.println("======");
            System.out.println("======");
        }
        
        return "paywinner?faces-redirect=true";
    }

    public List<String> getEmailWinners() {
        List<String> list = new ArrayList<String>();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        try {
            String sql = "select u.email from project353.users u join project353.winnner w using(USER_ID) where w.paid = FALSE";
            Statement s = DBConn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                String email = rs.getString("EMAIL");
                list.add(email);
            }
            DBConn.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    public List<Winner> getWinnerList() throws SQLException {
        List<Winner> list = new ArrayList<Winner>();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        try {
            String sql = "SELECT * FROM PROJECT353.WINNNER WHERE PAID = FALSE";
            Statement s = DBConn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                Winner win= new Winner();
                win.setUserID(rs.getString("USER_ID"));
                win.setSubID(rs.getDouble("SUBMISSION_ID"));
                list.add(win);
            }
            DBConn.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String payWinners() {
        emails = getEmailWinners();
        for (int i = 0; i < emails.size(); i++) {
            email.winnerEmail(emails.get(i));
        }

        String retVal = null;
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        try {
            String sql = "update project353.winnner set PAID = TRUE where PAID = FALSE";
            Statement s = DBConn.createStatement();
            int i = s.executeUpdate(sql);
            DBConn.close();
            s.close();
            retVal = "goodadmin?faces-redirect=true";
        } catch (Exception e) {
            retVal = "";
        }
        return retVal;
    }



public List<Winner> getAllWinner() throws SQLException {
        List<Winner> list = new ArrayList<Winner>();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        try {
            String sql = "SELECT * FROM PROJECT353.WINNNER";
            Statement s = DBConn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                Winner win= new Winner();
                win.setUserID(rs.getString("USER_ID"));
                win.setSubID(rs.getDouble("SUBMISSION_ID"));
                list.add(win);
            }
            DBConn.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}