package pl.polsl.ismoil.atajanov.servlet.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pl.polsl.ismoil.atajanov.servlet.model.Employee;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-24T16:59:03")
@StaticMetamodel(Department.class)
public class Department_ { 

    public static volatile SingularAttribute<Department, String> departmentName;
    public static volatile SingularAttribute<Department, String> address;
    public static volatile SingularAttribute<Department, Integer> id;
    public static volatile ListAttribute<Department, Employee> employees;

}