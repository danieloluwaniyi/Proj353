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
        return retVal;
    }
}
