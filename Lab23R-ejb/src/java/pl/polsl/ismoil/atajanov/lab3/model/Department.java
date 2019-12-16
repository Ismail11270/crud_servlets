package pl.polsl.ismoil.atajanov.lab3.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Employee table definition
 * @author Ismail
 * @version 1.0
 */
@Entity
@Table(name = "department")
@NamedQueries({
    @NamedQuery(name = Department.FIND_ALL,
            query = "SELECT d FROM Department d")})
public class Department implements Serializable {
    /**
     * String containing name of the named query
     */
    public static final String FIND_ALL = "Department.findAll";
    
    
    /**
     * Constructor with parameters
     * @param name name of the department
     * @param address address of the department
     */
    public Department(String name, String address){
        this.departmentName = name;
        this.address = address;
    }
    
    /**
     * Default constructor
     */
    public Department(){
        
    }
    /**
     * Primary key value
     */
    @Id
    @Column(name = "department_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Department Name column
     */
    @Column(name = "name",length = 50,nullable = false)
    private String departmentName;
    
    /**
     * Department Address column
     */
    @Column(name = "address",length = 50)
    private String address;
    
    /**
     * List of all employees
     */
    @OneToMany(mappedBy = "department", cascade=CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "employees_list")
    private List<Employee> employees = new ArrayList<>();
    
   
    /**
     * id getter
     * @return current id of the department
     */
    public Integer getId() {
        return id == null ? null : id;
    }
    
    /**
     * Set an id of the department
     * @param id new id
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * Department's name getter
     * @return current name
     */
    public String getDepartmentName() {
        if(departmentName.length() == 0) return "---";
        return departmentName;
    }

    /**
     * Set a name for the department
     * @param departmentName new department name
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    
    /**
     * Department's address getter
     * @return current address  
     */
    public String getAddress() {
        if(address.length() == 0) return "---";
        return address;
    }

    /**
     * Set an address of the department
     * @param address new address
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * Get all the employees of the department
     * @return a list of Employee
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Set employees of the department
     * @param employees list of employees
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * override of base hashCode() method
     * @return int hash code
     */
    @Override
    public int hashCode() {
        int hash = 213;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * override of base equals() method
     * @param object other object to compare this object with
     * @return true if objects are equal or false if not
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Department)) {
            return false;
        }
        Department other = (Department) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Override of toString() method
     * @return a composite String containing all the fields
     */
    @Override
    public String toString() {
        return "id = " + this.id + ",\n"
                + "department name = " + this.departmentName + ",\n"
                + "address = " + this.address + ",\n"
                + "";
    }
    
}
