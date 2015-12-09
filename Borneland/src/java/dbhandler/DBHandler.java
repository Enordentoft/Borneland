package dbhandler;


import functions.PartComparator;
import functions.PlaceComparator;
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
    //ArrayList<ScoreObject> rankedObjectList;
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
/**
 * Sort by age and totalScore
 * insert Place for each age group
 * @return 
 */
  /*  public ArrayList<ScoreObject> setRanking(){
        
         //rankedObjectList = (ArrayList<ScoreObject>) getUpdatedScore().clone();
         rankedObjectList = (ArrayList<ScoreObject>) scoreObjectList.clone();
         Collections.sort(rankedObjectList,new PlaceComparator());
         for (int i = 0; i < 4; i++) {
             int place = 1;
             for for (int k = 0; k < 10; k++) {
                     
                 
 {
            if(ob.getAgeGroup().equals(""+i)){
                 ob.setPlace(place);
                 if(rankedObjectList.get(rankedObjectList.indexOf(ob)).getTotalScore() != ob.getTotalScore()){
                 place++;}
             }
                 
             }
             for (ScoreObject scoreObjectList1 : rankedObjectList) {
                     System.out.println("Placing: "+ scoreObjectList1.getPlace());
                 }
             
        }
         
         
    }
    
       return rankedObjectList;
    }*/
    
      public ArrayList<ScoreObject> setRanking(ArrayList<ScoreObject> list){
        
         //rankedObjectList = (ArrayList<ScoreObject>) getUpdatedScore().clone();
         //rankedObjectList = (ArrayList<ScoreObject>) scoreObjectList.clone();
         Collections.sort(list,new PlaceComparator());
         //loop each ageGroupID
         for (int i = 0; i < 4; i++) {
             int place = 1;
             //Loop throuhg list
             for (int k = 0; k < list.size(); k++) {
                  
               
 {          //If the object has the correct ageGroup
            if(list.get(k).getAgeGroup().equals(""+i)){
                 list.get(k).setPlace(place);
                
                 if(list.get(k+1).getTotalScore() != list.get(k).getTotalScore())
                 place++;
             }}
                    //to prevent out of bounds
                 if(k == list.size()-2){
                     if(list.get(k+1).getTotalScore() != list.get(k).getTotalScore()){
                         place++;
                     }
                     list.get(k+1).setPlace(place);
                     break;
                 } 
             }
             
             for (ScoreObject scoreObjectList1 : list) {
                     System.out.println("Placing: "+ scoreObjectList1.getPlace());
                 }
             
        }
        
         
         
       return list;
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
            //set place (ranking)
            setRanking(scoreObjectList);
            
            for (ScoreObject scoreObjectList1 : scoreObjectList) {
                System.out.println(scoreObjectList1.toString()+"\n");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        //return scoreObjectList;
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
        int waitTime = 2;
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
    
     

}
