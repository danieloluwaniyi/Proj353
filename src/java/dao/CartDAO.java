/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Order;
import model.Submission;
import model.Profile;

/**
 *
 * @author vyass
 */
public class CartDAO {
    
    private Profile profile;
    private Submission submission;
    private Order order;
    public boolean placeOrder(String id) throws SQLException{
      
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        boolean retVal = false;
        
        String subid="";
        int[] subs = order.getCart();
        for(int i =0; i < subs.length;i++){
            
        }
        
        
        String insertString = "INSERT INTO ORDERS (firstName, lastName, userName, Email, Password, Paid) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = DBConn.prepareStatement(insertString);
            pstmt.setString(1, profile.getFirstName());
            pstmt.setString(2, profile.getLastName());
            pstmt.setString(3, profile.getUserID());
            pstmt.setString(4, profile.getEmail());
            pstmt.setString(5, profile.getPassword());
            pstmt.setBoolean(6, profile.getPaid());
        
        
        
        
        
        
        return retVal;
    }
}
