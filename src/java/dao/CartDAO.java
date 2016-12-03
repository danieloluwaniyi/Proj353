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
    
    public double cartLength(){
        int[] subs = order.getCart();
        return subs.length;
    
    }
    
    
    public double totalGet(String q){
        double totalPrice = 0;   
        Connection DBConn = null;
        try{
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            String myDB = "jdbc:derby://localhost:1527/project353";
            Connection DBConn1 = DBHelper.connect2DB(myDB, "itkstu", "student");
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(q);
            while(rs.next()){
                  totalPrice += rs.getDouble("Price");
            }
        }    
            
        catch(Exception e){
            System.err.println("problem");
        }
        return totalPrice;
     }
        
    public boolean placeOrder(String id) throws SQLException{
      
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        boolean retVal = false;
        
        
        double total =0;
        String subid="";
        int[] subs = order.getCart();
        double price =0;
        String user_id="";
        
        for(int i=0;i<subs.length;i++){
            String selectString = "select USER_ID from Project353.Users u join PROJECT353.SUBMISSIONS s using(USER_ID) where SUBMISSION_ID = ?";
            PreparedStatement pstmt = DBConn.prepareStatement(selectString);
            pstmt.setInt(1, subs[i]);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
               user_id = rs.getString("user_ID");
            }
            String selectString2 = "select price from PROJECT353.SUBMISSIONS where SUBMISSION_ID = ?";
            PreparedStatement pstmt1 = DBConn.prepareStatement(selectString2);
            pstmt.setInt(1, subs[i]);
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                price = rs1.getInt("PRICE");
            }
            double royalty = price *0.20;
            
            String insertString = "insert into royalty (USER_ID,SUBMISSION_ID,ROYALTY_AMOUNT) VALUES (?,?,?)";
            PreparedStatement pstmt2 = DBConn.prepareStatement(insertString);
            pstmt2.setString(1, user_id);
            pstmt2.setInt(2,subs[i]);
            pstmt2.setDouble(3, price);
            pstmt2.execute();
        }
        
        
        
        
        for(int i =0; i < subs.length;i++){
            subid = subs[i]+";";
        }
        
        
        
       
        for (int i =0; i<subs.length;i++){
            String getpricequery = "Select price from submissions werere submission_id="+subs[i];
            total = total + totalGet(getpricequery);
        }
        
        
        String insertString = "INSERT INTO ORDERS (User_id, submission_id, total) VALUES (?, ?, ?)";
        PreparedStatement pstmt = DBConn.prepareStatement(insertString);
            pstmt.setString(1, profile.getUserID());
            pstmt.setString(2, subid);
            pstmt.setDouble(3, total);
            pstmt.execute();
        return retVal;
    }
}
