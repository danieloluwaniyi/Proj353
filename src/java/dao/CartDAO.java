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
import java.util.List;
import model.Order;
import model.OrderItems;
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
        
    public boolean placeOrder(String userId,Order order,double price) throws SQLException{
      
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        boolean retVal = false;
        
        
        double total =price;
        String subid="";
        List<OrderItems> subs = order.getCart();
        
        for(int i =0; i < subs.size();i++){
            subid += subs.get(i).getItem().getSubmissionId()+";";
        }
        
        
        
        String insertString = "INSERT INTO Project353.ORDERS (User_id, submission_id, total) VALUES (?, ?, ?)";
        PreparedStatement pstmt = DBConn.prepareStatement(insertString);
            pstmt.setString(1, userId);
            pstmt.setString(2, subid);
            pstmt.setDouble(3, total);
            pstmt.execute();
        return retVal;
    }
}
