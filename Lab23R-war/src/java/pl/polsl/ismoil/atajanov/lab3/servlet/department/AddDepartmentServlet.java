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
 * Servlet used to add departments
 * @author Ismail
 * @version 1.0
 */
public class AddDepartmentServlet extends HttpServlet {

    /**
     * Ejb injection
     */
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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>[CRUD]Add department</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Adding new Department</h1>");
            out.println("<h3>Total amount of operations = " + getOperationCounter(request) + "</h3>");
            out.println("<form action=\"department/add\" method=\"post\" >");
            out.println("<input type=\"text\" placeholder=\"Enter department name\" name=\"dep_name\"/>");
            out.println("<input type=\"text\" placeholder=\"Enter department address\" name=\"dep_address\"/>");
            out.println("<input type=\"submit\" value=\"GO\"/>");
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

        //Counter
        incrementCounter(request);

        //adding
        try (PrintWriter out = response.getWriter()) {
            String departmentName = request.getParameter("dep_name");
            String departmentAddress = request.getParameter("dep_address");
            List<String> errors = new ArrayList<>();
            if (departmentName.equals("")) {
                errors.add("Department name cannot be empty!");
            }
            if (departmentAddress.equals("")) {
                errors.add("Department address cannot be empty");
            }
            if (!errors.isEmpty()) {
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("/error/add").forward(request, response);
            }
            Department department = new Department(departmentName, departmentAddress);
            departmentService.addDepartment(department);
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>[CRUD]Add Department </title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Department added!</h1>");
            out.println("<p>" + department.toString() + "</p>");
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
        String name = "add_department";
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
        String name = "add_department";
        if (counterMap.containsKey(name)) {
            return counterMap.get(name);
        } else {
            return 0;
        }
    }
}
