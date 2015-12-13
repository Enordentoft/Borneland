/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servletapplication;

import functions.ScoreObject;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michael
 */
public class TableHandler {

    private String tableRow;
    private String tableRound;
    
     private int fLane = 1;
     private int lLane = 10;
     
     

    /**
     * Creates headings for the table, and closes when done
     * 
     * @param out
     * @param list
     * @param ageGroupIn
     * @param wantedLane
     * @throws SQLException 
     */
    public void TableGenerator(PrintWriter out, ArrayList<ScoreObject> list, String ageGroupIn, int wantedLane) throws SQLException {
        
        //If a wantedLane is specifid // use regex?
        if(wantedLane != 0){
            fLane = wantedLane;
            lLane = wantedLane+1;
        }else{
            fLane = 1;
            lLane = 10;
        }        
        //print for each lane - 10 lanes hardcoded
        for (int l = fLane; l < lLane; l++) {
            int numberOfRounds = 10;
            int ageGroup = 0;
            //Find number of rounds for the current lane
            for (ScoreObject ob : list) {
                if (ob.getLaneID().equals("" + l)) {
                    numberOfRounds = Integer.parseInt(ob.getNumberOfRounds());
                    ageGroup = Integer.parseInt(ob.getAgeGroup());
                    break;
                }
            }
            //Loops through each object
            for (int i = 0; i < list.size(); i++) {
                //only generate lanes with the correct ageGroup        
                if (ageGroup != Integer.parseInt(ageGroupIn)) {
                    break;
                }
                // if its the first object, create column labels
                if (i == 0) {

                    out.println("<table border=\"1\">\n"
                            + "<thead >\n"
                            + "<tr>\n"
                            + "<th>ParticipantID</th>\n"
                            + "<th>FirstName</th>\n"
                            + "<th>LastName</th>\n");
                    //Print headings for numberOfRounds in current lane
                    for (int r = 1; r < numberOfRounds + 1; r++) {
                        out.println("<th>Round" + r + "</th>\n");
                    }
                    out.println("<th>Score</th>\n"
                            + "<th>Place</th>\n"
                            + "</tr>\n"
                            + "</thead>");
                   
                    if (list.get(i).getAgeGroup().equals(ageGroupIn) && list.get(i).getLaneID().equals("" + l)) {
                        out.println(addTableRow(list.get(i).getParticipantID(), list.get(i).getfName(), list.get(i).getlName(), list.get(i).getRoundNumber(), list.get(i).getNumberOfRounds(), list.get(i).getResultList(), "" + list.get(i).getPlace(), list.get(i).getTotalScore()));
                    }
                    //add a row for each participant object & check if its the correct lane
                } else if (list.get(i).getAgeGroup().equals(ageGroupIn) && list.get(i).getLaneID().equals("" + l)) {
                    out.println(addTableRow(list.get(i).getParticipantID(), list.get(i).getfName(), list.get(i).getlName(), list.get(i).getRoundNumber(), list.get(i).getNumberOfRounds(), list.get(i).getResultList(), "" + list.get(i).getPlace(), list.get(i).getTotalScore()));

                }
                //if its the last object, end the table
                if (i == list.size() - 1) {
                    out.println("<br>");
                    out.println("Lane: " + l);
                    out.println("</tbody>\n" + "</table>");

                }
            }
        }
    }
    

    /**
     * adds a row for each participant object
     *
     * @param partID
     * @param fName
     * @param lName
     * @param round
     * @param numberOfRounds
     * @param result
     * @param place
     * @param totalScore
     * @return
     */
    public String addTableRow(String partID, String fName, String lName, String round, String numberOfRounds, List<String> result, String place, int totalScore) {
        tableRound = "";
        tableRow = "<tbody>\n"
                + "<tr>\n"
                + "                    <td>" + partID + "</td>\n"
                + "                    <td>" + fName + "</td>\n"
                + "                    <td>" + lName + "</td>\n";

        //adds row cells based on numberOfRounds
        for (int i = 0; i < Integer.parseInt(numberOfRounds); i++) {
            //adds round scores
            tableRound += "<td>" + ((i < result.size()) ? result.get(i) : "") + "</td>\n";
        }

        tableRow += tableRound;
        tableRow += "<td>" + totalScore + "</td>\n"
                + "<td>" + place + "</td>\n"
                +//11
                "</tr>";
        return tableRow;
    }

}
