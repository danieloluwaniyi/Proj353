package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Royalty;
import email.Email;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class RoyaltyDAO implements Serializable {
    
    private List<String> emails = new ArrayList<String>();
    private Email email = new Email();
    
  
    
    public List<String> getEmail(){
        List<String> list = new ArrayList<String>();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/doluwan_Fa2016_Project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        try {
            String sql = "select u.email from project353.users u join project353.ROYALTY r using(USER_ID) where r.ROYALTY_PAID = FALSE";
            Statement s = DBConn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                String email= rs.getString("EMAIL");
                list.add(email);
            }
            DBConn.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    
    }

    
    
    public List<Royalty> getRoyaltyList() throws SQLException {
        List<Royalty> list = new ArrayList<Royalty>();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/doluwan_Fa2016_Project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        try {
            String sql = "select * from project353.ROYALTY where ROYALTY_PAID = FALSE";
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

    public boolean payRoyalty() {
        //Will Create a mailing list
        emails = getEmail();
        //Iteration through mailing list and sendin them emails.
        for(int i=0; i<emails.size();i++){
            email.royaltyEmail(emails.get(i));
        }
        
        boolean retVal = false;
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/doluwan_Fa2016_Project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        try {
            String sql = "update project353.ROYALTY set ROYALTY_PAID = TRUE where ROYALTY_PAID = FALSE";
            Statement s = DBConn.createStatement();
            int i = s.executeUpdate(sql);
            DBConn.close();
            s.close();
            retVal = true;
            } catch (Exception e) {
            retVal = false;
        }
        return retVal;
    }
}
