
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
            //getScore();
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnecion() {
        return con;
    }
/*
    public List<String> getScore() throws SQLException {
        ArrayList<String> list = new ArrayList();
        try {
            String q = "EXECUTE getScoreFirst";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                list.add(rs.getString(1) + rs.getString(2) + rs.getString(3) + rs.getString(4) + rs.getString(5));
                System.out.println(
                 rs.getString(1)
                 + rs.getString(2));
                 }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;

    }*/

    public List<ScoreObject> getScore() throws SQLException {
        List<ScoreObject> list = new ArrayList();
        try {
            //String q = "SELECT * FROM BornelandDB.dbo.scores";
            String q = "EXECUTE getScoreFirst";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {

                list.add(new ScoreObject(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                /*System.out.println(
                 rs.getString(1)
                 + rs.getString(2));
                 }*/

            }
            list = getNames(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;

    }

    public List<ScoreObject> getNames(List<ScoreObject> list) {
        List<ScoreObject> list2 = new ArrayList<>();
        try {
            for (int i = 0; i < list.size(); i++) {

               
              String q = "EXECUTE getScoreSecond @participantID=" + list.get(i).getParticipantID();
             //   String q = "select participants.firstName, participants.lastName, races.laneID, races.numberOfRounds \n" +
//"from participants, races where participants.participantID =" +list.get(i).getParticipantID()+" and races.participantID = "+list.get(i).getParticipantID();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(q);
                while (rs.next()) {                    
                 /*   System.out.println("**********************************************");                    
                    System.out.println(rs.getString(1));
                    System.out.println(rs.getString(2));
                    System.out.println(rs.getString("laneID"));
                    System.out.println(rs.getString("numberOfRounds"));*/
                    list.get(i).setfName(rs.getString(1));
                    list.get(i).setlName(rs.getString(2));
                    list.get(i).setLaneID(rs.getString(3));
                    list.get(i).setNumberOfRounds(rs.getString(4));
                    System.out.println(list.get(i).getfName());
                    System.out.println(list.get(i).getlName());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list2;
    }

}
