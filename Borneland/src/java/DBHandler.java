
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

    long lastUpdate, currentUpdate;
    List<ScoreObject> ScoreObjectList;
    Connection con;

    public DBHandler() {
        createConnection();
        lastUpdate = System.currentTimeMillis();
        ScoreObjectList = new ArrayList();

    }

    public void createConnection() {
        try {
            String connectionUrl = "jdbc:sqlserver://192.168.100.106;user=Michael;password=123;";
            con = DriverManager.getConnection(connectionUrl);
            System.out.println("Connected!");
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnecion() {
        return con;
    }

    public List<ScoreObject> getScore() throws SQLException {
        try {

            //String q = "SELECT * FROM BornelandDB.dbo.scores";
            String q = "EXECUTE getScoreFirst";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {

                ScoreObjectList.add(new ScoreObject(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));

            }
            // list = getNames(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getNames(ScoreObjectList);

    }

    public List<ScoreObject> getNames(List<ScoreObject> list) {
        try {
            for (int i = 0; i < list.size(); i++) {
                String q = "EXECUTE getScoreSecond @participantID=" + list.get(i).getParticipantID();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(q);
                while (rs.next()) {
                    list.get(i).setfName(rs.getString(1));
                    list.get(i).setlName(rs.getString(2));
                    list.get(i).setLaneID(rs.getString(3));
                    list.get(i).setNumberOfRounds(rs.getString(4));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Return a new ScoreObjectList from database if last update was more than
     * 20 sec, else return old list
     *
     * @return
     */
    public List<ScoreObject> getScoreList() {
        currentUpdate = System.currentTimeMillis();
        System.out.println("******************************************\n"
                + "getupdate time since last update:" + (currentUpdate - lastUpdate) / 1000);

        //Get list from database
        if ((currentUpdate - lastUpdate) / 1000 > 20 || ScoreObjectList.isEmpty()) {
            System.out.println("Returning scores from database");
            try {
                lastUpdate = System.currentTimeMillis();
                ScoreObjectList.clear();
                return getScore();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        System.out.println("returning old ScoreObjectList Because last update was done: \n " + (currentUpdate - lastUpdate) / 1000 + "seconds ago");
        //get old list
        return ScoreObjectList;

    }

}
