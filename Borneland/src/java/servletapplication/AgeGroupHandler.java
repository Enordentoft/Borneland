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

/**
 *
 * @author Michael
 */
public class AgeGroupHandler {
    

    public void TableGenerator(PrintWriter out, ArrayList<ScoreObject> list, ServletController controller, String ageGroupIn) throws SQLException {
        //List<ScoreObject> list = db.getScore();    
        //print for each lane - 10 lanes hardcoded
        for (int l = 1; l < 10; l++) {
            int numberOfRounds = 10;
            int ageGroup = 0;
            //Find number of rounds for the current lane
            for(ScoreObject ob : list){
                if (ob.getLaneID().equals(""+l)){
                    numberOfRounds = Integer.parseInt(ob.getNumberOfRounds());
                    ageGroup = Integer.parseInt(ob.getAgeGroup());
                    break;
                }
                
            }
            
            
        
        //Loops through each object
        for (int i = 0; i < list.size(); i++) {
            //only TableGenerator lanes with the correct ageGroup
            System.out.println("ageparamprinttestgo: " + ageGroupIn);            
            if(ageGroup != Integer.parseInt(ageGroupIn)){
                break;
            }
            //its the first object, create column labels
            if (i == 0) {
                
                
                out.println("<table border=\"1\">\n"
                        + "<thead >\n"
                        + "<tr>\n"
                        + "<th>ParticipantID</th>\n"
                        + "<th>FirstName</th>\n"
                        + "<th>LastName</th>\n");
                //Print headings for numberOfRounds in current lane
                for (int r = 1; r < numberOfRounds+1; r++) {
                     out.println( "<th>Round"+ r +"</th>\n");
                }                        
                       out.println( "<th>Score</th>\n"
                        + "<th>Place</th>\n"
                        + "</tr>\n"
                        + "</thead>");
                if (list.get(i).getAgeGroup().equals(ageGroupIn)&&list.get(i).getLaneID().equals(""+l)) {
                    out.println(controller.addTableRow(list.get(i).getParticipantID(), list.get(i).getfName(), list.get(i).getlName(), list.get(i).getRoundNumber(), list.get(i).getNumberOfRounds(), list.get(i).getResultList(), "place?", list.get(i).getTotalScore()));
                }
            //add a row for each participant object && if its the correct lane
            }  else if (list.get(i).getAgeGroup().equals(ageGroupIn)&&list.get(i).getLaneID().equals(""+l)) {
                out.println(controller.addTableRow(list.get(i).getParticipantID(), list.get(i).getfName(), list.get(i).getlName(), list.get(i).getRoundNumber(), list.get(i).getNumberOfRounds(), list.get(i).getResultList(), "place?", list.get(i).getTotalScore()));
            
            } 
             //if its the last object, end the table
            if (i == list.size()-1) {
                out.println("<br>");
                out.println("Lane: "+l);
                out.println("</tbody>\n" + "</table>");
       
            }
        

            /*
             out.println(list.get(i).getParticipantID());
             out.println(list.get(i).getfName());
             out.println(list.get(i).getlName());
             out.println(list.get(i).getRoundNumber());
             out.println(list.get(i).getResult());
             out.println("<br>");*/
        }}}
        
    
}
