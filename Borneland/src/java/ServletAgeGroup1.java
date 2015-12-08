/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Michael
 */
public class ServletAgeGroup1 extends HttpServlet {

    DBHandler db;
    String tableRow;
    String tableRound;

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        db = new DBHandler();

    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta http-equiv=\"refresh\" content=\"5\">");
            out.println("<title>Servlet ServletOne</title>");
            out.println("</head>");
            out.println("<body>");
            // out.println("<h1>Servlet ServletAllAgeGroups at " + request.getContextPath() + "</h1>");
            out.println("<h1>Age Group 1: Results for parents </h1>");
            print(out);            
            printRankings(out);            
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    public void printRankings (PrintWriter out){
        List<ScoreObject> list = db.getScoreForRanking();
         
        //print for each ageGroup
        for (int l = 1; l < 4; l++) {
            int numberOfRounds = 10;
            int ageGroup = 0;
            //Find number of rounds for the current lane
            for(ScoreObject ob : list){
                if (ob.getAgeGroup().equals(""+l)){
                    numberOfRounds = Integer.parseInt(ob.getNumberOfRounds());
                    ageGroup = Integer.parseInt(ob.getAgeGroup());
                    break;
                }
                
            }
            
            
        
        //Loops through each object
        for (int i = 0; i < list.size(); i++) {
            //only print lanes with the correct ageGroup
            if(ageGroup != l){
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
                if (list.get(i).getAgeGroup().equals("1")&&list.get(i).getLaneID().equals(""+l)) {
                    out.println(addTableRow(list.get(i).getParticipantID(), list.get(i).getfName(), list.get(i).getlName(), list.get(i).getRoundNumber(), list.get(i).getNumberOfRounds(), list.get(i).getResultList(), "place?", list.get(i).getTotalScore()));
                }
            //add a row for each participant object && if its the correct lane
            }  else if (list.get(i).getAgeGroup().equals("1")&&list.get(i).getLaneID().equals(""+l)) {
                out.println(addTableRow(list.get(i).getParticipantID(), list.get(i).getfName(), list.get(i).getlName(), list.get(i).getRoundNumber(), list.get(i).getNumberOfRounds(), list.get(i).getResultList(), "place?", list.get(i).getTotalScore()));
            
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
        }
        }
        
    }

    /*
     public void print (PrintWriter out) throws SQLException{
     List<ScoreO> list = db.getScore();
     for (int i = 0; i < list.size(); i++) {
     out.println(list.get(i)+"<br>");
     }}*/
    public void print(PrintWriter out) throws SQLException {
        //List<ScoreObject> list = db.getScore();
        List<ScoreObject> list = db.getUpdatedScore();
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
            //only print lanes with the correct ageGroup
            if(ageGroup != 1){
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
                if (list.get(i).getAgeGroup().equals("1")&&list.get(i).getLaneID().equals(""+l)) {
                    out.println(addTableRow(list.get(i).getParticipantID(), list.get(i).getfName(), list.get(i).getlName(), list.get(i).getRoundNumber(), list.get(i).getNumberOfRounds(), list.get(i).getResultList(), "place?", list.get(i).getTotalScore()));
                }
            //add a row for each participant object && if its the correct lane
            }  else if (list.get(i).getAgeGroup().equals("1")&&list.get(i).getLaneID().equals(""+l)) {
                out.println(addTableRow(list.get(i).getParticipantID(), list.get(i).getfName(), list.get(i).getlName(), list.get(i).getRoundNumber(), list.get(i).getNumberOfRounds(), list.get(i).getResultList(), "place?", list.get(i).getTotalScore()));
            
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
            //adds scores
            //System.out.println("+++++++++++++++++++++++++"+result.get(i));
            tableRound += "<td>" + ((i < result.size()) ? result.get(i) : "") + "</td>\n";
        }

        /* for (int i = 1; i < Integer.parseInt(numberOfRounds)+1; i++) {
         //adds score to the correct round
         tableRound += "<td>" + (Integer.parseInt(round) == i ? result : "")+"</td>\n";}*/
        tableRow += tableRound;
        tableRow += "<td>" + totalScore + "</td>\n"
                + "<td>" + place + "</td>\n"
                +//11
                "</tr>";
        return tableRow;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    

        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletAllAgeGroups.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletAllAgeGroups.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
