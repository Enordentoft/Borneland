package servletapplication;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import helperobjects.ScoreObject;
import dbhandler.DBHandler;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Michael
 */
public class ServletControllerAdminScore extends HttpServlet {

    private DBHandler db;
    private ArrayList<ScoreObject> list;
    private TableHandler handler;
    private String[] laneSelected, ageSelected, ageGroup; 

    @Override
    public void init() throws ServletException {
        super.init(); 
        db = new DBHandler();
        handler = new TableHandler();
        laneSelected = new String[11];
        ageSelected = new String[4];
        ageGroup = new String[]{"","0-6","7-9","10-14"};
        Arrays.fill(laneSelected, "");
        Arrays.fill(ageSelected, "");
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
        list = db.getUpdatedList();
   
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta http-equiv=\"refresh\" content=\"\">");
            out.println("<title>Servlet ServletOne</title>");
            out.println("</head>");
            out.println("<body>");  
            // Print heading with age
            out.println("<h1>Admin Score Input: Age Group " + ageGroup[Integer.parseInt(request.getParameter("ageDropDown"))] + "</h1>");
            //send printWriter, ScoreList and request
            requestChecker(out, list, request);                             
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    /**
     * 
     * @param out
     * @param list
     * @param request
     * @throws SQLException
     * @throws IOException
     * @throws ServletException 
     */
    public void requestChecker(PrintWriter out, ArrayList<ScoreObject> list, HttpServletRequest request) throws SQLException, IOException, ServletException{
     
            int laneID = Integer.parseInt(request.getParameter("SelectedLane"));           
            //array to control lane selected option in the html
            laneSelected[laneID] = "selected=\"selected\"";            
            //array to control age selected option in the html
            ageSelected[Integer.parseInt(request.getParameter("ageDropDown"))] = "selected=\"selected\"";             
            //print buttons and dropdowns
            out.println(" <form action=\"ServletControllerAdminScore\" method=\"POST\">   \n" +
"            <input type=\"hidden\" name=\"type\" value=\"scoreAdmin\" />\n" +
"\n" +
"            <input type=\"submit\" value=\"GetLane\" />\n" +
"            <select name=\"SelectedLane\">\n" +
"                <option "+ laneSelected[0]+" value=\"0\">Show All</option>\n" +
"                <option "+ laneSelected[1]+" value=\"1\">Lane 1</option>\n" +
"                <option "+ laneSelected[2]+" value=\"2\">Lane 2</option>\n" +
"                <option "+ laneSelected[3]+" value=\"3\">Lane 3</option>\n" +
"                <option "+ laneSelected[4]+" value=\"4\">Lane 4</option>\n" +
"                <option "+ laneSelected[5]+" value=\"5\">Lane 5</option>\n" +
"                <option "+ laneSelected[6]+" value=\"6\">Lane 6</option>\n" +
"                <option "+ laneSelected[7]+" value=\"7\">Lane 7</option>\n" +
"                <option "+ laneSelected[8]+" value=\"8\">Lane 8</option>\n" +
"                <option "+ laneSelected[9]+" value=\"9\">Lane 9</option>\n" +
"                <option "+ laneSelected[10]+" value=\"10\">Lane 10</option>\n" +
"            </select>\n" +
"\n" +
"            <select name=\"ageDropDown\" size=\"1\">\n" +
"                <option "+ ageSelected[1]+"value=\"1\">Age 0-6</option>\n" +
"                <option "+ ageSelected[2]+" value=\"2\">Age 7-9</option>\n" +
"                <option "+ ageSelected[3]+"value=\"3\">Age 10-14</option>\n" +
"\n" +
"            </select>   \n" +
"            \n" +
"            <br>\n" +
"            <br>\n" +
"            <select name=\"ParticipantID\">\n"
                    + "<option value=\"-\">ParticipantID -</option> ");
                    //method for printing participant id
                    participansOnLane(laneID, out);
out.println("            </select>\n" +
"            <select name=\"SelectRound\">\n" +
        "<option value=\"0\">Round -</option>\n" +
"                <option value=\"1\">Round 1</option>\n" +
"                <option value=\"2\">Round 2</option>\n" +
"                <option value=\"3\">Round 3</option>\n" +
"                <option value=\"4\">Round 4</option>\n" +
"                <option value=\"5\">Round 5</option>\n" +
"                <option value=\"6\">Round 6</option>\n" +
"                <option value=\"7\">Round 7</option>\n" +
"                <option value=\"8\">Round 8</option>\n" +
"            </select>\n" +
"            <select name=\"SetResult\" >\n" +
"                <option>-</option>\n" +
"                <option>0</option>\n" +
"                <option>1</option>\n" +
"            </select>\n" +
"            <input type=\"submit\" value=\"SubmitScore\" />  \n" +
"        </form>    ");            
            //Clear arrays for dropdown to prevent duplicates
            Arrays.fill(laneSelected, "");
            Arrays.fill(ageSelected, "");
            //check for input
             updateCheck(request.getParameter("SelectedLane"),request.getParameter("ParticipantID"),request.getParameter("SelectRound"),request.getParameter("SetResult"));
             // print selected table
             handler.TableGenerator(out, list, request.getParameter("ageDropDown"), Integer.parseInt(request.getParameter("SelectedLane")));        
    }
    
    /**
     * prints participant ID's to the scoreAdmin html dropdown
     * 
     * @param laneID
     * @param out 
     */
    public void participansOnLane(int laneID, PrintWriter out){
        ArrayList<ScoreObject> list = db.getParticipantsOnLane(laneID);
       
        if(list.size() > 0){
        for (int i = 0; i < list.size(); i++) {
            out.println(" <option  value=\""+ list.get(i).getParticipantID()+"\">ParticipantID "+list.get(i).getParticipantID()+"</option>");
        }               
        }else{
             out.println("<option>ParticipantID -</option> ");
        }
    }
    
    /**
     * If all parameters for the score update has been filled out, then insert into the database
     * @param laneID
     * @param participantID
     * @param roundNumber
     * @param result 
     */
    public void updateCheck(String laneID, String participantID, String roundNumber, String result){
        
       if(!laneID.equals("-")&&!participantID.equals("-")&&!roundNumber.equals("-")&&!result.equals("-")){
           db.updateScore(laneID, participantID, roundNumber, result);     
       }    
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
