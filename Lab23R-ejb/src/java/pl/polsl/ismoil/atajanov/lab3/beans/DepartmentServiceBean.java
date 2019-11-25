package pl.polsl.ismoil.atajanov.lab3.beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.polsl.ismoil.atajanov.lab3.model.Department;

/**
 *
 * @author Ismail
 */
@Stateless
@LocalBean
public class DepartmentServiceBean {

    @PersistenceContext
    EntityManager em;

    public Department findDepartmentById(int id) {
        return em.find(Department.class, id);
    }

    public void addDepartment(Department department) {
        em.persist(department);
    }

    public List<Department> getAllDepartments() {
        return em.createNamedQuery(Department.FIND_ALL).getResultList();
    }

    public void updateDepartment(Department department) {
            em.merge(department);
    }
    
    public void deleteDepartment(Integer id){
        em.remove(findDepartmentById(id));
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
