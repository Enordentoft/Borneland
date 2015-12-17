package dbhandler;

import helperobjects.NumberInLaneComparator;
import helperobjects.PlaceComparator;
import helperobjects.ScoreObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
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

    private long lastUpdate, currentUpdate;
    private long waitTime = 2;
    private ArrayList<ScoreObject> scoreObjectList;    
    private Connection con;

    public DBHandler() {
        createConnection();
        lastUpdate = System.currentTimeMillis();
        scoreObjectList = new ArrayList();

    }

    /**
     * create connection for the database
     */
    private void createConnection() {
        try {
            //String connectionUrl = "jdbc:sqlserver://192.168.100.106;user=Michael;password=123;";
            String connectionUrl = "jdbc:sqlserver://212.112.129.191;user=Michael;password=123;";
            con = DriverManager.getConnection(connectionUrl);
            System.out.println("Connected!");
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    public Connection getConnecion() {               
        return con;
    }

    /**
     * Calculates ranking and adds it to the objects in the list
     * @param list
     * @return 
     */
    public ArrayList<ScoreObject> getRankedList() {
        ArrayList<ScoreObject> list = getUpdatedList();
        //sort age, score - needed for the method to work
        Collections.sort(list, new PlaceComparator());
        //loop each ageGroupID
        for (int i = 1; i < 4; i++) {
            int place = 1;
            //keep track of the index for the current agegroup
            int deltaIndex = 0;
            //Loop throuhg list
            for (int k = 0; k < list.size(); k++) {

                {          //If the object has the correct ageGroup
                    if (list.get(k).getAgeGroup().equals("" + i)) {
                        if (deltaIndex < 1) {
                            list.get(k).setPlace(place);
                        } else if (list.get(k).getTotalScore() == list.get(k - 1).getTotalScore()) {
                            list.get(k).setPlace(place);
                        } else if (list.get(k).getTotalScore() != list.get(k - 1).getTotalScore()) {
                            place++;
                            list.get(k).setPlace(place);
                        }

                        deltaIndex++;
                    }

                }
            }
        }
        return list;
    }
    
    /**
     * Main method for returning a scorelist, used in the getUpdatedList method
     * @return
     * @throws SQLException 
     */
    private ArrayList<ScoreObject> getDatabaseScoreList() throws SQLException {
        try {         
            String q = "EXECUTE getScoreFirst";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                if (checkID(rs.getString("participantID"), rs.getString("result"))) {                                
                } else {                    
                    scoreObjectList.add(new ScoreObject(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                }

            }          
            //get names etc for the objects
            getNames(scoreObjectList);         
            //sort by numerInLane
            Collections.sort(scoreObjectList, new NumberInLaneComparator());

        } catch (Exception e) {
            e.printStackTrace();
        }        
        return scoreObjectList;
    }
    
   
    /**
     * returns true if the id already exists, and adds result to the existing
     * object
     *
     * @param ID
     * @param result
     * @return
     */
    private boolean checkID(String ID, String result) {
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

    /**
     * Adds name and other extra data to the ScoreObjects
     *
     * @param list
     * @return
     */
    private ArrayList<ScoreObject> getNames(ArrayList<ScoreObject> list) {
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
     * get participants on a specifact lane, used for dropdown menu in score admin
     * @param laneID
     * @return 
     */
    public ArrayList<ScoreObject> getParticipantsOnLane(int laneID) {
        ArrayList<ScoreObject> list = new ArrayList<>();
        for (ScoreObject obj : getUpdatedList()) {
            if (obj.getLaneID().equals("" + laneID)) {
                list.add(obj);
            }
        }
        return list;
    }

    /**
     * Return a new scoreObjectList from database if last update was more than
     * 30 sec, else return old list
     *
     * @return
     */
    public ArrayList<ScoreObject> getUpdatedList() {
        currentUpdate = System.currentTimeMillis();
        waitTime = 30;
        System.out.println("Time since last update:" + (currentUpdate - lastUpdate) / 1000);

        //Get list from database
        if ((currentUpdate - lastUpdate) / 1000 > waitTime || scoreObjectList.isEmpty()) {
            System.out.println("Returning scores from database");
            try {
                lastUpdate = System.currentTimeMillis();
                scoreObjectList.clear();
                return getDatabaseScoreList();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        System.out.println("returning old ScoreObjectList Because last update was done: \n" + (currentUpdate - lastUpdate) / 1000 + "seconds ago");
        //get old list
        return scoreObjectList;

    }
    
    /**
 * reset database updatetimer
 */
    public void resetUpdateTimer() {
        lastUpdate -= (waitTime * 1000);
    }



    /**
     * update a participants score for a specific round
     *
     * @param laneID
     * @param participantID
     * @param roundNumber
     * @param result
     */
    public void updateScore(String laneID, String participantID, String roundNumber, String result) {
        try {
            String sql = "EXECUTE updateScore @laneID = " + laneID + ", @participantID = " + participantID
                    + ", @roundNumber = " + roundNumber + ", @result =  " + result;
            Statement st = con.createStatement();
            int rowsAffected = st.executeUpdate(sql);
            System.out.println("ROWS AFFECTED IN DB UPDATE" + rowsAffected);
            System.out.println("laneID" + laneID + "participantID" + participantID + "roundNumber" + roundNumber + "result" + result);
            //reset update time
            resetUpdateTimer();     
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * create participant in the database, and add rounds based on agegroup
     * @param fName1
     * @param lName
     * @param ageGroupID
     * @param email
     * @param laneID
     * @return
     * @throws SQLException 
     */
    public ResultSet createParticipant(String fName, String lName, String ageGroupID, String email, String laneID) throws SQLException {
        String sql = "EXECUTE createParticipant @firstName = '" + fName + "', @lastName = '" + lName + "', @ageGroupID = " + ageGroupID + ", @email = '" + email + "', @laneID = " + laneID;
        Statement st = con.createStatement();
        boolean createCheck = st.execute(sql);
        sql = "EXECUTE returnCreateParticipant @laneID = " + laneID;
        ResultSet rs = st.executeQuery(sql);        
        int rounds;
        
        if(ageGroupID.equals("1")){
            rounds = 6;
        }else {
            rounds = 8;
        }
        
        for (int i = 0; i < rounds; i++) {
            sql = "EXECUTE createScore @laneID = "+laneID;
            st = con.createStatement();
            st.execute(sql);
            
        }
        return rs;
    }
    
    
}
