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
    Connection con;
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
           // out.println("<h1>Servlet ServletAgeGroup1 at " + request.getContextPath() + "</h1>");
            out.println("<h1>ServletOne: Results for parents </h1>");
            print(out);
            out.println("</body>");
            out.println("</html>");
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
        List<ScoreObject> list = db.getScoreList();
        //Loops through each object
        for (int i = 0; i < list.size(); i++) {
            //its the first object, create column labels
            if (i == 0) {
                out.println("<table border=\"1\">\n"
                + "<thead >\n"
                    + "<tr>\n"
                        + "<th>ParticipantID</th>\n"
                        + "<th>FirstName</th>\n"
                        + "<th>LastName</th>\n"
                        + "<th>Round1</th>\n"
                        + "<th>Round2</th>\n"
                        + "<th>Round3</th>\n"
                        + "<th>Round4</th>\n"
                        + "<th>Round5</th>\n"
                        + "<th>Round6</th>\n"
                        + "<th>Score</th>\n"
                        + "<th>Place</th>\n" 
                   + "</tr>\n"
                + "</thead>");

                out.println(addTableRow(list.get(i).getParticipantID(),list.get(i).getfName(),list.get(i).getlName(),list.get(i).getRoundNumber(),list.get(i).getNumberOfRounds()
                     ,list.get(i).getResultList().get(i),"place?"));
                //if its the last object, end the table
            } else if (i == list.size()) {
              out.println("</tbody>\n" + "</table>");
              //add a row for each participant object
            } else {
             out.println(addTableRow(list.get(i).getParticipantID(),list.get(i).getfName(),list.get(i).getlName(),list.get(i).getRoundNumber(),list.get(i).getNumberOfRounds()
                     ,list.get(i).getResultList().get(i),"place?"));
        

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

    /**
     *adds a row for each participant object
     * @param partID
     * @param fName
     * @param lName
     * @param round
     * @param numberOfRounds
     * @param result
     * @param place
     * @return
     */
    public String addTableRow(String partID, String fName, String lName, String round,String numberOfRounds, String result, String place) {
        tableRound = "";
        tableRow = "<tbody>\n" +
                   "<tr>\n" +
"                    <td>"+partID+"</td>\n" +
"                    <td>"+fName+"</td>\n" +
"                    <td>"+lName+"</td>\n"; 
                //adds row cells based on numberOfRounds
                 for (int i = 1; i < Integer.parseInt(numberOfRounds)+1; i++) {
                     //adds score to the correct round
                     tableRound += "<td>" + (Integer.parseInt(round) == i ? result : "")+"</td>\n";}
        
             tableRow += tableRound;    
             tableRow +=  "<td>"+result+"</td>\n" +
                        "<td>"+place+"</td>\n" +//11
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
        if (request.getParameter("fname").equals("333")) {
            System.out.println("doGet in servlet");
        }

        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletAgeGroup1.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ServletAgeGroup1.class.getName()).log(Level.SEVERE, null, ex);
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
