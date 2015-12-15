package dbhandler;

import functions.PlaceComparator;
import functions.ScoreObject;
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

    long lastUpdate, currentUpdate;
    long waitTime = 2;
    ArrayList<ScoreObject> scoreObjectList;
    //ArrayList<ScoreObject> rankedObjectList;
    Connection con;

    public DBHandler() {
        createConnection();
        lastUpdate = System.currentTimeMillis();
        scoreObjectList = new ArrayList();

    }

    private void createConnection() {
        try {
            String connectionUrl = "jdbc:sqlserver://192.168.100.106;user=Michael;password=123;";
            con = DriverManager.getConnection(connectionUrl);
            System.out.println("Connected!");
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //should be singleton?   
    public Connection getConnecion() {
        return con;
    }

    private ArrayList<ScoreObject> getRanking(ArrayList<ScoreObject> list) {
        //loop each ageGroupID
        for (int i = 1; i < 4; i++) {
            int place = 1;
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

    /*
     public ArrayList<ScoreObject> getRanking(ArrayList<ScoreObject> list) {

     Collections.sort(list, new PlaceComparator());
     //loop each ageGroupID
     for (int i = 0; i < 4; i++) {
     int place = 1;
     int deltaIndex = 0;
     //Loop throuhg list
     for (int k = 0; k < list.size(); k++) {
              

     {          //If the object has the correct ageGroup
     if (list.get(k).getAgeGroup().equals("" + i)) {
     deltaIndex++;
     list.get(k).setPlace(""+place);

     // If the next participant does not have the same score increase place
     if (list.get(k + 1).getTotalScore() != list.get(k).getTotalScore()) {
     place++;
     //add X to result to indicate rematch on tie score
     }else if(deltaIndex < 4 && list.get(k + 1).getTotalScore() == list.get(k).getTotalScore()){
     list.get(k).setPlace("" + place+"X");
     list.get(k+1).setPlace("" + place+"X");
     k++;
     }
     }
     }
     // handles last element, to prevent out of bounds exception
     if (k == list.size() - 2) {
     if (list.get(k + 1).getTotalScore() != list.get(k).getTotalScore()) {
     place +=1;
     list.get(k + 1).setPlace(""+place);
     break;
     }else if(list.get(k + 1).getTotalScore() == list.get(k).getTotalScore()){     
     place +=1;
     if(deltaIndex < 4){
     //list.get(k).setPlace("" + place+"X");
     list.get(k+1).setPlace("" + place+"X");
     }else{
     list.get(k+1).setPlace("" + place+"x");
     }
     //k++;
     }else {
     //list.get(k).setPlace("" + place+"X");
     place +=1;
     list.get(k+1).setPlace("" + place+"Xx");
     break;
     }

     }
            
     }
     }

     return list;
     }*/
    private ArrayList<ScoreObject> getScore() throws SQLException {
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
            //sort by lane and totalScore
            //Collections.sort(scoreObjectList, new PartComparator());

            //get names
            getNames(scoreObjectList);
            Collections.sort(scoreObjectList, new PlaceComparator());
            //set place (ranking)
            getRanking(scoreObjectList);

            for (ScoreObject ob : scoreObjectList) {
                System.out.println(ob.toString());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //return scoreObjectList;
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

    public ArrayList<String> getParticipantsOnLane(int laneID) {
        ArrayList<String> list = new ArrayList<>();
        for (ScoreObject obj : getUpdatedList()) {
            if (obj.getLaneID().equals("" + laneID)) {
                list.add(obj.getParticipantID());
            }
        }
        return list;
    }

    /**
     * Return a new scoreObjectList from database if last update was more than
     * 20 sec, else return old list
     *
     * @return
     */
    public ArrayList<ScoreObject> getUpdatedList() {
        currentUpdate = System.currentTimeMillis();
        waitTime = 20;
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
            //EXECUTE updateScore @laneID = 1, @participantID = 1, @roundNumber = 1, @result = 1
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

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

        //System.out.println("createCheck dbHandler" +createCheck);
        return rs;
    }

    public void resetUpdateTimer() {
        lastUpdate -= (waitTime * 1000);
    }

}
