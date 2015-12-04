/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michael
 */
public class ScoreObject {
    private String scoreID;
    private String participantID;
    private String raceID;
    private String roundNumber;
    private String result;
    private String fName;
    private String lName;
    private String laneID;
    private String numberOfRounds;
    
    public ScoreObject(String scoreID, String participantID, String raceID, String roundNumber, String result){     
        this.scoreID = scoreID;
        this.participantID = participantID;
        this.raceID = raceID;
        this.roundNumber = roundNumber;
        this.result = result;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getLaneID() {
        return laneID;
    }

    public void setLaneID(String laneID) {
        this.laneID = laneID;
    }

    public String getNumberOfRounds() {
        return numberOfRounds;
    }

    public void setNumberOfRounds(String numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }
    
    

  
    

    public String getScoreID() {
        return scoreID;
    }

    public String getParticipantID() {
        return participantID;
    }

    public String getRaceID() {
        return raceID;
    }

    public String getRoundNumber() {
        return roundNumber;
    }

    public String getResult() {
        return result;
    }

    
    
    
}
