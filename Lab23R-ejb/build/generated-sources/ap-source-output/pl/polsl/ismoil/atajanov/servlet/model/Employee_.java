package pl.polsl.ismoil.atajanov.servlet.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pl.polsl.ismoil.atajanov.servlet.model.Department;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-24T16:59:03")
@StaticMetamodel(Employee.class)
public class Employee_ { 

    public static volatile SingularAttribute<Employee, String> fullName;
    public static volatile SingularAttribute<Employee, Integer> id;
    public static volatile SingularAttribute<Employee, Department> department;
    public static volatile SingularAttribute<Employee, Date> birthDate;

}