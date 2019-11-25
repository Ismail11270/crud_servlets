package pl.polsl.ismoil.atajanov.lab3.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to redirect to CRUD servlet According to the operation and
 * entity selecteion
 *
 * @author Ismail
 * @version 1.0
 */
public class CrudServlet extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String entitySelection = request.getParameter("entity_selection");
        int operationSelection = Integer.parseInt(request.getParameter("operation_selection"));
        switch (operationSelection) {
            case 1:
                request.getRequestDispatcher("/" + entitySelection + "/add").
                        forward(request, response);
            case 2:
                request.getRequestDispatcher("/" + entitySelection + "/find").
                        forward(request, response);
            case 3:
                request.getRequestDispatcher("/" + entitySelection + "/update").
                        forward(request, response);
            case 4:
                request.getRequestDispatcher("/" + entitySelection + "/delete").
                        forward(request, response);
        }

    }

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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
