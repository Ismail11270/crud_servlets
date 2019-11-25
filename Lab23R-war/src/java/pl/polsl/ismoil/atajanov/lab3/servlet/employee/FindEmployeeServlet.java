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
import pl.polsl.ismoil.atajanov.lab3.model.Department;
import pl.polsl.ismoil.atajanov.lab3.model.Employee;

/**
 * Servlet used to find employees
 * @author Ismail
 * @version 1.0
 */
public class FindEmployeeServlet extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>[CRUD]Find employee(s)</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Finding an Employee</h1>");
            out.println("<h3>Total amount of operations = " + getOperationCounter(request) + "</h3>");
            out.println("<form action='employee/find?op=2' method='POST'>");
            out.println("<input type='submit' value='Find All Employees'/><br/>");
            out.println("</form><br/>");
            out.println("<form action='employee/find?op=1' method='POST'>");
            out.println("<label>Find Employee By Id</label><br/>");
            out.println("<input type='number' name='emp_id' placeholder='Employee id...'/><br/>");
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
        List<Employee> employees = new ArrayList<>();
        if (op.equals("1")) {
            String empId = request.getParameter("emp_id");
            Integer id = 0;
            try {
                id = Integer.parseInt(empId);
            } catch (NumberFormatException nfe) {
                errors.add("Wrong id format, can't be empty or float.");
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("/error/delete").forward(request, response);
            }
            Employee employee = employeeService.findEmployeeById(id);
            if (employee == null) {
                errors.add("Employee with given id doesn't exist!");
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("/error/delete").forward(request, response);
            } else {
                employees.add(employee);
            }
        } else if (op.equals("2")) {
            employees = employeeService.getAllEmployees();
        }
        try (PrintWriter out = response.getWriter()) {
            out.println("<table width='100%' border='1'>");
            out.println("<tr><th>id</th><th>Employee name</th><th>Employee birthdate</th><th>Department id</th></tr>");
            for (Employee e : employees) {
                out.println("<tr><td>" + e.getId() + "</td>");
                out.println("<td>" + e.getFullName()+ "</td>");
                out.println("<td>" + e.getBirthDate().toString() + "</td>");
                out.println("<td>" + e.getDepartment().getId() + "</td>");
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
        String name = "find_employee";
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
        String name = "find_employee";
        if (counterMap.containsKey(name)) {
            return counterMap.get(name);
        } else {
            return 0;
        }
    }

}
