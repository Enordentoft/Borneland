package functions;

import java.util.ArrayList;
import java.util.List;

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
    private String numberInLane;
    private String ageGroup;
    private String raceID;
    private String roundNumber;
    private String fName;
    private String lName;
    private String laneID;
    private String numberOfRounds;
    private int place;
    private List<String> resultList;
    private int totalScore;

    public ScoreObject(String scoreID, String participantID, String raceID, String roundNumber, String result) {
        totalScore = 0;
        this.scoreID = scoreID;
        this.participantID = participantID;
        this.raceID = raceID;
        this.roundNumber = roundNumber;
        resultList = new ArrayList<>();
        addResultToList(result);
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getAgeGroup() {
        return ageGroup;
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

    public String getNumberInLane() {
        return numberInLane;
    }

    public void setNumberInLane(String numberInLane) {
        this.numberInLane = numberInLane;
    }   
    

    public String getRaceID() {
        return raceID;
    }

    public String getRoundNumber() {
        return roundNumber;
    }

    public List<String> getResultList() {
        return resultList;
    }

    public final void addResultToList(String result) {
        if (result == null) {
            resultList.add("-");
        } else {
            resultList.add(result);
            totalScore += Integer.parseInt(result);
        }

    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "TotalScore:" + totalScore + " scoreID: " + scoreID + " participantID: " + participantID + " raceID: " + raceID + " roundNumber: " + roundNumber
                + " result: " + resultList + " fName: " + fName + " lName: " + lName + " laneID: " + laneID + " numberOfRounds: " + numberOfRounds;

    }

}
