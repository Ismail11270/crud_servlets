package pl.polsl.ismoil.atajanov.lab3.beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.polsl.ismoil.atajanov.lab3.model.Department;

/**
 * Bean for performing CRUD operations on department table
 * @author Ismail
 * @version 1.0
 */
@Stateless
@LocalBean
public class DepartmentServiceBean {

    
    /**
     * Entity manager used perform operations
     */
    @PersistenceContext
    EntityManager em;

    /**
     * Finding a department with a given id
     * @param id id of the department to look for
     * @return department object
     */
    public Department findDepartmentById(int id) {
        return em.find(Department.class, id);
    }

    /**
     * Adding a department to the table
     * @param department department object to add
     */
    public void addDepartment(Department department) {
        em.persist(department);
    }

    /**
     * Finding all departments in the table
     * @return list of departments
     */
    public List<Department> getAllDepartments() {
        return em.createNamedQuery(Department.FIND_ALL).getResultList();
    }

    /**
     * Updating department data
     * @param department new department
     */
    public void updateDepartment(Department department) {
            em.merge(department);
    }
    
    /**
     * Deleting department from the table
     * @param id id of the department to delete
     */
    public void deleteDepartment(Integer id){
        em.remove(findDepartmentById(id));
    }

}
