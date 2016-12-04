
import dao.DBHelper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;



@ManagedBean
@SessionScoped
public class RoyaltyBean {

    public List<Royalty> getRoyaltyList() {
        List<Royalty> list = new ArrayList<Royalty>();
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/project353", "itkstu", "student");
            String sql = "select * from ROYALTY where ROYALTY_PAID = ?";
            ps = con.prepareStatement(sql);
            ps.setBoolean(1, false);
            rs = ps.executeQuery();
            while (rs.next()) {
                Royalty roy = new Royalty();
                roy.setUserID("USER_ID");
                roy.setSubID(rs.getDouble("SUBMISSION_ID"));
                roy.setAmount(rs.getDouble("ROYALTY_AMOUNT"));
                list.add(roy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
