/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.ismoil.atajanov.lab3.servlet.employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.ismoil.atajanov.lab3.beans.EmployeeServiceBean;

/**
 * Servlet used to delete employees
 * @author Ismail
 * @version 1.0
 */
public class DeleteEmployeeServlet extends HttpServlet {

    /**
     * Ejb injection
     */
    @EJB
    EmployeeServiceBean employeeService;

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
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>[CRUD]Delete employee</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Deleting an Employee</h1>");
            out.println("<h3>Total amount of operations = " + getOperationCounter(request) + "</h3>");
            out.println("<form action=\"employee/delete\" method=\"POST\" >");
            out.println("<br/><label>Enter an id of an employee you want to delete, make sure it exists.</label></br>");
            out.println("<input type=\"number\" placeholder=\"Employee id...\" name=\"emp_id\"/>");
            out.println("<br/><input type=\"submit\" value=\"DELETE\"/>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
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

        response.setContentType("text/html;charset=UTF-8");
        List<String> errors = new ArrayList<>();

        //COunter
        incrementCounter(request);

        try (PrintWriter out = response.getWriter()) {
            String empId = request.getParameter("emp_id");
            Integer id = 0;
            try {
                id = Integer.parseInt(empId);
            } catch (NumberFormatException nfe) {
                errors.add("Wrong id format, can't be empty or float.");
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("/error/delete").forward(request, response);
            }
            if (employeeService.findEmployeeById(id) == null) {
                errors.add("Department with given id doesn't exist!");
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("/error/delete").forward(request, response);
            } else {
                employeeService.deleteEmployee(id);
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>[CRUD]Delete employee</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Deleting an Employee</h1>");
                out.println("<p>Employee deleted successfully!</p>");
                out.println("</br><a href=\"" + request.getContextPath() + "/\">Go back</a>");
                out.println("</body>");
                out.println("</html>");
            }
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
    }

    /**
     * Increment operation counter
     *
     * @param request servlet request
     */
    private void incrementCounter(HttpServletRequest request) {
        HttpSession session = request.getSession();

        Map<String, Integer> counterMap
                = (Map<String, Integer>) session.getAttribute("counterMap");

        if (counterMap == null) {
            counterMap = new HashMap<>();
        }
        String name = "delete_employee";
        if (counterMap.containsKey(name)) {
            counterMap.put(name, counterMap.get(name) + 1);
        } else {
            counterMap.put(name, 1);
            session.setAttribute("counterMap", counterMap);
        }
    }

    /**
     * Method returns counter of performed operations
     *
     * @param request servlet request
     * @return number of performed operations
     */
    private int getOperationCounter(HttpServletRequest request) {
        HttpSession session = request.getSession();

        Map<String, Integer> counterMap
                = (Map<String, Integer>) session.getAttribute("counterMap");
        if (counterMap == null) {
            return 0;
        }
        String name = "delete_employee";
        if (counterMap.containsKey(name)) {
            return counterMap.get(name);
        } else {
            return 0;
        }
    }

}
