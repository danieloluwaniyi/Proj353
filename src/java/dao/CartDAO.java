/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;
import model.Order;
import model.OrderItems;
import model.Submission;
import model.Profile;

/**
 *
 * @author vyass
 */
@ManagedBean
@ApplicationScoped
public class CartDAO {

    private Profile profile;
    private Submission submission;
    private Order order;


    public boolean placeOrder(String buyerId, Order order, double price) throws SQLException {

        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        boolean retVal = false;
        String userId="";

        double total = price;
        String subid = "";
        List<OrderItems> subs = order.getCart();

            for (int i = 0; i < subs.size(); i++) {
                subid += subs.get(i).getItem().getSubmissionId() + ";";
            }

            for (int i = 0; i < subs.size(); i++) {
                String selectString = "select USER_ID from Project353.Users u join PROJECT353.SUBMISSIONS s using(USER_ID) where SUBMISSION_ID = ?";
                PreparedStatement pstmt = DBConn.prepareStatement(selectString);
                pstmt.setInt(1, subs.get(i).getItem().getSubmissionId());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    userId = rs.getString("user_ID");
                }
                String selectString2 = "select price from PROJECT353.SUBMISSIONS where SUBMISSION_ID = ?";
                PreparedStatement pstmt1 = DBConn.prepareStatement(selectString2);
                pstmt1.setInt(1, subs.get(i).getItem().getSubmissionId());
                ResultSet rs1 = pstmt1.executeQuery();
                while (rs1.next()) {
                    price = rs1.getInt("PRICE");
                }
                double royalty = price * 0.20;

                String insertString = "insert into Project353.royalty (USER_ID,SUBMISSION_ID,ROYALTY_AMOUNT) VALUES (?,?,?)";
                PreparedStatement pstmt2 = DBConn.prepareStatement(insertString);
                pstmt2.setString(1, userId);
                pstmt2.setInt(2, subs.get(i).getItem().getSubmissionId());
                pstmt2.setDouble(3, royalty);
                pstmt2.execute();
            }

            String insertString = "INSERT INTO Project353.ORDERS (User_id, submission_id, total) VALUES (?, ?, ?)";
            PreparedStatement pstmt = DBConn.prepareStatement(insertString);
            pstmt.setString(1, buyerId);
            pstmt.setString(2, subid);
            pstmt.setDouble(3, total);
            pstmt.execute();
            return retVal;
        }
    }
