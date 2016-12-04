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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class RoyaltyBean implements Serializable {
    
    private List<String> emails = new ArrayList<String>();
    private Email email = new Email();
    
   
    
    public List<String> getEmail(){
        List<String> list = new ArrayList<String>();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        try {
            String sql = "select u.email from project353.users u join project353.ROYALTY r using(USER_ID) where ROYALTY_PAID = FALSE";
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
        String myDB = "jdbc:derby://localhost:1527/project353";
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

    public String payRoyalty() {
        //Will Create a mailing list
        emails = getEmail();
        //Iteration through mailing list and sendin them emails.
        for(int i=0; i<emails.size();i++){
            email.royaltyEmail(emails.get(i));
        }
        
        String retVal = null;
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/project353";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        try {
            String sql = "update project353.ROYALTY set ROYALTY_PAID = TRUE where ROYALTY_PAID = FALSE";
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
