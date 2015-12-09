package dbhandler;


import functions.PartComparator;
import functions.RankComparator;
import functions.ScoreObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
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
    ArrayList<ScoreObject> scoreObjectList;
    ArrayList<ScoreObject> rankedObjectList;
    Connection con;

    public DBHandler() {
        createConnection();
        lastUpdate = System.currentTimeMillis();
        scoreObjectList = new ArrayList();
       

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

    public List<ScoreObject> getScoreForRanking(){
         rankedObjectList = (ArrayList<ScoreObject>) getUpdatedScore().clone();
         Collections.sort(getUpdatedScore(),new RankComparator());
       return rankedObjectList;
    }
    
    public ArrayList<ScoreObject> getScore() throws SQLException {
        try {

            //String q = "SELECT * FROM BornelandDB.dbo.scores";
            String q = "EXECUTE getScoreFirst";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                if (checkID(rs.getString("participantID"), rs.getString("result"))) {
                    // System.out.println("id already exists *****************************");             
                } else {
                    // System.out.println("id did not exist, added ***************************");
                    scoreObjectList.add(new ScoreObject(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                }

            }
            //get names
            scoreObjectList = getNames(scoreObjectList);
            //sort by lane and totalScore
            Collections.sort(scoreObjectList, new PartComparator());
            
            for (ScoreObject scoreObjectList1 : scoreObjectList) {
                System.out.println(scoreObjectList1.toString()+"\n");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scoreObjectList;

    }

    public boolean checkID(String ID, String result) {
        if (scoreObjectList.isEmpty()) {
            return false;
        } else {
            for (int i = 0; i < scoreObjectList.size(); i++) {
                if (scoreObjectList.get(i).getParticipantID().equals(ID)) {
                    scoreObjectList.get(i).addResultToList(result);
                    return true;
                }

            }

            return false;
        }
    }

    public ArrayList<ScoreObject> getNames(ArrayList<ScoreObject> list) {
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
                    list.get(i).setAgeGroup(rs.getString(5));
                  

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Return a new scoreObjectList from database if last update was more than
     * 20 sec, else return old list
     *
     * @return
     */
    public ArrayList<ScoreObject> getUpdatedScore() {
        currentUpdate = System.currentTimeMillis();
        int waitTime = 20;
        System.out.println("Time since last update:" + (currentUpdate - lastUpdate) / 1000);

        //Get list from database
        if ((currentUpdate - lastUpdate) / 1000 > waitTime || scoreObjectList.isEmpty()) {
            System.out.println("Returning scores from database");
            try {
                lastUpdate = System.currentTimeMillis();
                scoreObjectList.clear();
                return getScore();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        System.out.println("returning old ScoreObjectList Because last update was done: \n" + (currentUpdate - lastUpdate) / 1000 + "seconds ago");
        //get old list
        return scoreObjectList;

    }
    
    public void getRankings(ArrayList<ScoreObject> list){
        
        
        
    }
    

}
