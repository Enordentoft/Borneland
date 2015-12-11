package servletapplication;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import functions.ScoreObject;
import dbhandler.DBHandler;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Michael
 */
public class ServletController extends HttpServlet {

    DBHandler db;
    ArrayList<ScoreObject> list;
    AgeGroupHandler handler;
    
    String updateTime = "4";

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        db = new DBHandler();
        handler = new AgeGroupHandler();
        

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
        list = db.getUpdatedScore();
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta http-equiv=\"refresh\" content=\""+updateTime+"\">");
            out.println("<title>Servlet ServletOne</title>");
            out.println("</head>");
            out.println("<body>");         
            out.println("<h1>Age Group: "+request.getParameter("ageDropDown") +" Results for parents </h1>");
            //send printWriter, ScoreList, check what ageGroup have been selected, specify lane or set to 0 to show all
            requestChecker(out, list, request, response);
            //handler.TableGenerator(out, list, request.getParameter("ageDropDown"), 0);                      
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    public void requestChecker(PrintWriter out, ArrayList<ScoreObject> list, HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException{
        
//if request contains the SelectLane dropdown (from InputScore.html)
        if(request.getParameter("SelectedLane") != (null)){         
            out.println("<form action=\"ServletController\" method=\"GET\"> \n" +
"\n" +
"            <input type=\"submit\" value=\"GetLane\" />\n" +
"            <select name=\"SelectedLane\">\n" +
"                <option value=\"0\">Show All</option>\n" +
"                <option value=\"1\">Lane 1</option>\n" +
"                <option value=\"2\">Lane 2</option>\n" +
"                <option value=\"3\">Lane 3</option>\n" +
"                <option value=\"4\">Lane 4</option>\n" +
"                <option value=\"5\">Lane 5</option>\n" +
"                <option value=\"6\">Lane 6</option>\n" +
"                <option value=\"7\">Lane 7</option>\n" +
"                <option value=\"8\">Lane 8</option>\n" +
"                <option value=\"9\">Lane 9</option>\n" +
"                <option value=\"10\">Lane 10</option>\n" +
"            </select>\n" +
"\n" +
"            <select name=\"ageDropDown\" size=\"1\">\n" +
"                <option value=\"1\">Age 0-6</option>\n" +
"                <option value=\"2\">Age 7-9</option>\n" +
"                <option value=\"3\">Age 10-14</option>\n" +
"\n" +
"            </select>\n" +
"\n" +
"            <br>\n" +
"            <br>\n" +
"            <select name=\"ParticipantID\">\n" +
"                <option>ParticipantID</option>           \n" +
"            </select>\n" +
"            <select name=\"SelectRound\">\n" +
"                <option value=\"1\">Round 1</option>\n" +
"                <option value=\"1\">Round 2</option>\n" +
"                <option value=\"1\">Round 3</option>\n" +
"                <option value=\"1\">Round 4</option>\n" +
"                <option value=\"1\">Round 5</option>\n" +
"                <option value=\"1\">Round 6</option>\n" +
"            </select>\n" +
"            <select name=\"SetResult\" >\n" +
"                <option>-</option>\n" +
"                <option>0</option>\n" +
"                <option>1</option>\n" +
"            </select>\n" +
"            <input type=\"submit\" value=\"SubmitScore\" />\n" +
"        </form>");            
             handler.TableGenerator(out, list, request.getParameter("ageDropDown"), Integer.parseInt(request.getParameter("SelectedLane")));
        }else{
        handler.TableGenerator(out, list, request.getParameter("ageDropDown"), 0); 
            
        }
        //System.out.println("/////////"+request.getParameter("ageDropDown").toString());
        
    }
    
    
    
    
    /*public void print(PrintWriter out) throws SQLException {
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
        

            
             out.println(list.get(i).getParticipantID());
             out.println(list.get(i).getfName());
             out.println(list.get(i).getlName());
             out.println(list.get(i).getRoundNumber());
             out.println(list.get(i).getResult());
             out.println("<br>");
        }
        }

    }*/

   

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
          ex.printStackTrace();
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
           ex.printStackTrace();
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
