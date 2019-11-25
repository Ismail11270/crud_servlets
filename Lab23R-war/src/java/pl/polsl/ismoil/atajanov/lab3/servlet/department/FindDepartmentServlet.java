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
 *
 * @author Ismail
 */
public class FindDepartmentServlet extends HttpServlet {

    @EJB
    DepartmentServiceBean departmentService;

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
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>[CRUD]Find department(s)</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Finding a Department</h1>");
            out.println("<h3>Total amount of operations = " + getOperationCounter(request) + "</h3>");
            out.println("<form action='department/find?op=2' method='POST'>");
            out.println("<input type='submit' value='Find All Departments'/><br/>");
            out.println("</form><br/>");
            out.println("<form action='department/find?op=1' method='POST'>");
            out.println("<label>Find Department By Id</label><br/>");
            out.println("<input type='number' name='dep_id' placeholder='Department id...'/><br/>");
            out.println("<input type='submit' value='Find'/>");
            out.println("</form><br/>");

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

        incrementCounter(request);

        String op = request.getParameter("op");
        List<String> errors = new ArrayList<>();
        List<Department> departments = new ArrayList<>();
        if (op.equals("1")) {
            String depId = request.getParameter("dep_id");
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
            } else {
                departments.add(department);
            }
        } else if (op.equals("2")) {
            departments = departmentService.getAllDepartments();
        }
        try (PrintWriter out = response.getWriter()) {
            out.println("<table width='100%' border='1'>");
            out.println("<tr><th>id</th><th>Department Name</th><th>Department Address</th></tr>");
            for (Department d : departments) {
                out.println("<tr><td>" + d.getId() + "</td>");
                out.println("<td>" + d.getDepartmentName() + "</td>");
                out.println("<td>" + d.getAddress() + "</td>");
            }
            out.println("</table>");
            out.println("</br><a href=\"" + request.getContextPath() + "/\">Go back</a>");
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
        String name = "find_department";
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
        String name = "find_department";
        if (counterMap.containsKey(name)) {
            return counterMap.get(name);
        } else {
            return 0;
        }
    }

}
