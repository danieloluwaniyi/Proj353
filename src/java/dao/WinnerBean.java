/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import email.Email;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Royalty;

/**
 *
 * @author vyass
 */
@ManagedBean
@SessionScoped
public class WinnerBean implements Serializable {

    private List<String> emails = new ArrayList<String>();
    private Email email = new Email();

    public void selectWinner() {
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        try {
            String Query = "SELECT submission_id FROM Project353.submissions where {fn TIMESTAMPDIFF( SQL_TSI_DAY, CURRENT DATE, Submission_date)}<=7 and rating= (select max(rating) from Project353.submissions)";
            Statement s = DBConn.createStatement();
            ResultSet rs = s.executeQuery(Query);
            Statement s1 = DBConn.createStatement();
          
            while (rs.next()) {
                double winner_id = rs.getDouble("Submission_id");
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

    }

    public void setWinners() {
        selectWinner();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        try {
            String Query = "SELECT * FROM Project353.submissions where winner = true";
            Statement s = DBConn.createStatement();
            ResultSet rs = s.executeQuery(Query);
//            Statement s1 = DBConn.createStatement();
            while (rs.next()) {
                double winner_id = rs.getDouble("Submission_id");
                String user_id = rs.getString("USER_ID");
//                String updateQuery = "UPDATE PROJECT353.SUBMISSIONS SET WINNER = TRUE WHERE SUBMISSION_ID = " + winner_id;
//                int i = s1.executeUpdate(updateQuery);
                String winner_table = "insert into winners (user_id,Submission_id) values(" + user_id + "," + winner_id + ")";
                s.executeQuery(winner_table);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getEmailWinners() {
        List<String> list = new ArrayList<String>();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        try {
            String sql = "select u.email from project353.users u join project353.winner w using(USER_ID) where w.paid = FALSE";
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

    public List<Royalty> getWinnerList() throws SQLException {
        List<Royalty> list = new ArrayList<Royalty>();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        try {
            String sql = "select * from project353.winner where paid = FALSE";
            Statement s = DBConn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                Royalty roy = new Royalty();
                roy.setUserID(rs.getString("USER_ID"));
                roy.setSubID(rs.getDouble("SUBMISSION_ID"));
                roy.setAmount(rs.getDouble("ROYALTY_AMOUNT"));
                list.add(roy);
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

}
