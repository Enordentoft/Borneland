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
    
    private String updateTime = "4";
    private String updateTimeAdmin = "";
    private String age = "0";

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
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
      /*  if(ageGroup[Integer.parseInt(request.getParameter("ageDropDown"))] != null){
        age = ageGroup[Integer.parseInt(request.getParameter("ageDropDown"))];            
        }*/
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta http-equiv=\"refresh\" content=\""+(request.getParameter("type".equals("scoreAdmin") ? updateTimeAdmin : updateTime))+"\">");
            out.println("<title>Servlet ServletOne</title>");
            out.println("</head>");
            out.println("<body>");         
            //out.println("<h1>Age Group " + age + ": Results for parents </h1>");
            out.println("<h1>Age Group " + request.getParameter("ageDropDown") + ": Results for parents </h1>");
            //send printWriter, ScoreList, check what ageGroup have been selected, specify lane or set to 0 to show all
            requestChecker(out, list, request);
            //handler.TableGenerator(out, list, request.getParameter("ageDropDown"), 0);                      
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    public void requestChecker(PrintWriter out, ArrayList<ScoreObject> list, HttpServletRequest request) throws SQLException, IOException, ServletException{
        
//if request contains the SelectLane dropdown (from InputScore.html)
            System.out.println("//////////////" +request.getParameter("type"));
           // System.out.println("//////////////" + laneSelected[1]);  
            if(request != null){
        if(request.getParameter("type").equals("scoreAdmin")){  
            int laneID = Integer.parseInt(request.getParameter("SelectedLane"));
            //Stop updating when in scoreAdmin mode
            //updateTime = "";
            //lane
            laneSelected[laneID] = "selected=\"selected\""; 
            //"+ laneSelected[0]+"
            ageSelected[Integer.parseInt(request.getParameter("ageDropDown"))] = "selected=\"selected\""; 
            //"+ ageSelected[1]+"         
            
            
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
                    + "<option value=\"-\">ParticipantID</option> ");
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
"                <option value=\"7\">Round 6</option>\n" +
"                <option value=\"8\">Round 6</option>\n" +
"            </select>\n" +
"            <select name=\"SetResult\" >\n" +
"                <option>-</option>\n" +
"                <option>0</option>\n" +
"                <option>1</option>\n" +
"            </select>\n" +
"            <input type=\"submit\" value=\"SubmitScore\" />  \n" +
"        </form>    ");       
            System.out.println(laneSelected[5]);
            //Clear arrays to prevent duplicates
            Arrays.fill(laneSelected, "");
            Arrays.fill(ageSelected, "");
             handler.TableGenerator(out, list, request.getParameter("ageDropDown"), Integer.parseInt(request.getParameter("SelectedLane")));
                         updateCheck(request.getParameter("SelectedLane"),request.getParameter("ParticipantID"),request.getParameter("SelectRound"),request.getParameter("SetResult"));

        }else if(request.getParameter("type").equals("index")){
            //set update time for parents
           // updateTime = "2";
            //shows all lanes because last parameter is 0
        handler.TableGenerator(out, list, request.getParameter("ageDropDown"), 0); 
            
        }}
        //System.out.println("/////////"+request.getParameter("ageDropDown").toString());
        
    }
    
    /**
     * prints participant ID's to the scoreAdmin html
     * 
     * @param laneID
     * @param out 
     */
    public void participansOnLane(int laneID, PrintWriter out){
        ArrayList<String> list = db.getParticipantsOnLane(laneID);
       
        if(list.size() >0){
        for (int i = 0; i < list.size(); i++) {
            out.println(" <option  value=\""+ list.get(i)+"\">ParticipantID "+list.get(i)+"</option>");
        }               
        }else{
             out.println("<option>ParticipantID -</option> ");
        }
        //participansOnLane(laneID, out);
    }
    
    public void updateCheck(String laneID, String participantID, String roundNumber, String result){
        
       if(!laneID.equals("-")&&!participantID.equals("-")&&!roundNumber.equals("-")&&!result.equals("-")){
           db.updateScore(laneID, participantID, roundNumber, result);
           System.out.println("UPDATE SUCCESS!!");
       }
        System.out.println("////////////" + "DID NOT UPDATE");
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
