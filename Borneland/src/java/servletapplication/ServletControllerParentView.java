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
public class ServletControllerParentView extends HttpServlet {

    private DBHandler db;
    private ArrayList<ScoreObject> list;
    private TableHandler handler;
    private String[] laneSelected, ageSelected, ageGroup; 
    
    private String updateTime = "2";   

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
        list = db.getRankedList();
      /*  if(ageGroup[Integer.parseInt(request.getParameter("ageDropDown"))] != null){
        age = ageGroup[Integer.parseInt(request.getParameter("ageDropDown"))];            
        }*/
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta http-equiv=\"refresh\" content=\""+ updateTime +"\">");
            out.println("<title>Servlet ServletOne</title>");
            out.println("</head>");
            out.println("<body>");         
            //out.println("<h1>Age Group " + age + ": Results for parents </h1>");
            out.println("<h1>Results: Age Group " + ageGroup[Integer.parseInt(request.getParameter("ageDropDown"))] + "</h1>");
            //send printWriter, ScoreList, check what ageGroup have been selected, specify lane or set to 0 to show all
            requestChecker(out, list, request);
            //handler.TableGenerator(out, list, request.getParameter("ageDropDown"), 0);                      
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    public void requestChecker(PrintWriter out, ArrayList<ScoreObject> list, HttpServletRequest request) throws SQLException, IOException, ServletException{
        
            handler.TableGenerator(out, list, request.getParameter("ageDropDown"), 0); 
        
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
