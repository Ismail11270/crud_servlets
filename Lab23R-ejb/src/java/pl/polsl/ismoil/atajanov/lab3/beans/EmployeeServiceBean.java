package pl.polsl.ismoil.atajanov.lab3.beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.polsl.ismoil.atajanov.lab3.model.Department;
import pl.polsl.ismoil.atajanov.lab3.model.Employee;

/**
 * Bean for performing CRUD operations on employee table
 *
 * @author Ismail
 * @version 1.0
 */
@Stateless
@LocalBean
public class EmployeeServiceBean {

    /**
     * Entity manager used perform operations
     */
    @PersistenceContext
    EntityManager em;
    
    /**
     * Finding an employee with a given id
     * @param id id of the employee to look for
     * @return employee object
     */
    public Employee findEmployeeById(int id) {
        return em.find(Employee.class, id);
    }

    /**
     * Adding an employee to the table
     * @param employee employee object to add
     */
    public void addEmployee(Employee employee) {
        em.persist(employee);
    }

    
    /**
     * Finding all employees in the table
     * @return list of employees
     */
    public List<Employee> getAllEmployees() {
        return em.createNamedQuery(Employee.FIND_ALL).getResultList();
    }

    /**
     * Updating employee data
     * @param employee new employee
     */
    public void updateEmployee(Employee employee) {
        em.merge(employee);
    }

    
    /**
     * Deleting employee from the table
     * @param id id of the employee to delete
     */
    public void deleteEmployee(Integer id) {
        em.remove(findEmployeeById(id));
    }
}
