package pl.polsl.ismoil.atajanov.lab3.servlet.employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import java.util.Date;
import pl.polsl.ismoil.atajanov.lab3.beans.DepartmentServiceBean;

/**
 * Servlet used to add employees
 * @author Ismail
 * @version 1.0
 */
public class AddEmployeeServlet extends HttpServlet {

    
    /**
     * Ejb injection
     */
    @EJB
    EmployeeServiceBean employeeService;

    
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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>[CRUD]Add employee</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Adding new employee</h1>");
            out.println("<h3>Total amount of operations = " + getOperationCounter(request) + "</h3>");
            out.println("<form action=\"employee/add\" method=\"post\" >");
            out.println("Employee name<input type=\"text\" placeholder=\"Employee's full name\" name=\"emp_name\"/><br/>");
            out.println("Date of birth<input type=\"text\" placeholder=\"mm/dd/yyyy\" name=\"emp_bd\"/><br/>");
            out.println("Department id<input type=\"number\" placeholder=\"Employee's department id\" name=\"emp_dep\"/><br/>");
            out.println("<input type=\"submit\" value=\"ADD\"/>");
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
            String fullName = request.getParameter("emp_name");
            String depId = request.getParameter("emp_dep");
            String birthDate = request.getParameter("emp_bd");
            Date date = null;
            Integer id = -1;
            try {
                date = new SimpleDateFormat("MM/dd/yyyy").parse(birthDate);
            } catch (ParseException e) {
                 errors.add("Invalid date input!");
            }
            if (fullName.equals("")) {
                errors.add("Employee's name cannot be empty!");
            }
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
            }
            if (!errors.isEmpty()) {
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("/error/add").forward(request, response);
            }
            
            Employee employee = new Employee(fullName, date, department);
            employeeService.addEmployee(employee);
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>[CRUD]Add Employee </title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Employee added!</h1>");
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
        String name = "add_employee";
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
        String name = "add_employee";
        if (counterMap.containsKey(name)) {
            return counterMap.get(name);
        } else {
            return 0;
        }
    }

}
