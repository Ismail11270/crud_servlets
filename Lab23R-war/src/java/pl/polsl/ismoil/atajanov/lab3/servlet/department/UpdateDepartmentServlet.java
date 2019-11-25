/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.ismoil.atajanov.lab3.servlet.department;

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
import pl.polsl.ismoil.atajanov.lab3.beans.DepartmentServiceBean;
import pl.polsl.ismoil.atajanov.lab3.model.Department;

/**
 * Servlet used to update departments
 * @author Ismail
 * @version 1.0
 */
public class UpdateDepartmentServlet extends HttpServlet {

    /**
     * Ejb injection
     */
    @EJB
    DepartmentServiceBean departmentService;


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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>[CRUD]Update department</title>");
            out.println("<h2>leave field blank if no change is required</h2>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Updating a Department</h1>");
            out.println("<h3>Total amount of operations = " + getOperationCounter(request) + "</h3>");
            out.println("<form action=\"department/update\" method=\"post\" >");
            out.println("<br/><label>Enter an id of department you want to update, make sure it exists.</label></br>");
            out.println("<br/><input style='margin: 10px' type=\"number\" placeholder=\"Department id...\" name=\"dep_id\"/>");
            out.println("<br/><input style='margin: 10px' type=\"text\" placeholder=\"New Department name...\" name=\"dep_name\"/>");
            out.println("<br/><input style='margin: 10px' type=\"text\" placeholder=\"New Department address...\" name=\"dep_address\"/>");
            out.println("<br/><input style='margin: 10px' type=\"submit\" value=\"UPDATE\"/>");
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

        incrementCounter(request);

        try (PrintWriter out = response.getWriter()) {
            String depId = request.getParameter("dep_id");
            String newName = request.getParameter("dep_name");
            String newAddress = request.getParameter("dep_address");

            Integer id = 0;
            try {
                id = Integer.parseInt(depId);
            } catch (NumberFormatException nfe) {
                errors.add("Wrong id format, can't be empty or float.");
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("/error/delete").forward(request, response);
            }
            Department department = departmentService.findDepartmentById(id);
            if (department == null) {
                errors.add("Department with given id doesn't exist!");
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("/error/delete").forward(request, response);
            }
            if (!newName.equals("")) {
                department.setDepartmentName(newName);
            }
            if (!newAddress.equals("")) {
                department.setAddress(newAddress);
            }
            departmentService.updateDepartment(department);
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>[CRUD]Update department</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Updating a Department</h1>");
            out.println("<p>Department updated successfully!</p>");
            out.println("</br><a href=\"" + request.getContextPath() + "/\">Go back</a>");
            out.println("</body>");
            out.println("</html>");
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
        String name = "update_department";
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
        String name = "update_department";
        if (counterMap.containsKey(name)) {
            return counterMap.get(name);
        } else {
            return 0;
        }
    }
}
