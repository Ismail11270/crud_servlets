package pl.polsl.ismoil.atajanov.lab3.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Employee table definition
 * @author Ismail
 * @version 1.1
 */
@Entity
@Table(name = "employee")
@NamedQueries({
    @NamedQuery(name = "Employee.findAll",
            query = "SELECT e FROM Employee e")
})
public class Employee implements Serializable{
    /**
     * String containing name of the named query
     */
    public static final String FIND_ALL = "Employee.findAll";
    
    /**
     * Primary key value
     */
    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Employee's name column
     */
    @Column(name = "full_name",nullable = false,length = 50)
    private String fullName;
    
    /**
     * Employee's birth date column
     */
    @Column(name = "birth_date",nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    /**
     * Employee's department column
     */
    @ManyToOne
    @JoinColumn(name="department_id",nullable = false)
    private Department department;

    /**
     * Employee's Name getter
     * @return Employee's name
     */
    public String getFullName() {
        if(fullName.length() == 0) return "---";
        return fullName;
    }
    /**
     * Employee's Name setter
     * @param fullName new name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    /**
     * Employee's BirthDate getter
     * @return Employee's birth date
     */
    public Date getBirthDate() {
        return birthDate;
    }
    /**
     * Employee's BirthDate setter
     * @param birthDate new birth date
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    /**
     * Employee's Department getter
     * @return Employee's department
     */
    public Department getDepartment() {
        return department;
    }
    
    
    /**
     * Employee's Department setter
     * @param department new department 
     */
    public void setDepartment(Department department) {
        this.department = department;
    }
    
        
    
    /**
     * Employee's Id getter
     * @return id of the employee
     */
    public Integer getId() {
        return id;
    }
    /**
     * Employee's id setter
     * @param id new id
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * override of base hashCode() method
     * @return int hash code
     */
    @Override
    public int hashCode() {
        int hash = 123;
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
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
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
    public String toString(){
        return "Id = " + id + "\n"
                + "Full Name: " + fullName + "\n"
                + "Birth Date: " + birthDate.toString() + "\n"
                + "Department id: " + department.getId() + "\n";
    }
}
