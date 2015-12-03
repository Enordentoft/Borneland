
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Michael
 */
public class DBHandler {

    Connection con;

    public DBHandler() {
        try {
            //System.err.println("start connection try");
            // String connectionUrl = "jdbc:sqlserver://localhost;user=sa;password=waterline;";
            String connectionUrl = "jdbc:sqlserver://192.168.100.106;user=Michael;password=123;";
            con = DriverManager.getConnection(connectionUrl);           
            System.out.println("Connected!");
            testConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnecion() {
        return con;
    }

    public void testConnection() {
        try {
            String q = "SELECT * FROM BornelandDB.dbo.lanes";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                System.out.println(
                        rs.getString(1)
                        + rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServletOne.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
