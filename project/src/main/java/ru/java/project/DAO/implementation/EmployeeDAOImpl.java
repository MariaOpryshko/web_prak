package ru.java.project.DAO.implementation;


import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.java.project.DAO.interfaces.EmployeeDAO;
import ru.java.project.bd_classes.basic.Employee;


import java.util.List;

@Repository
public class EmployeeDAOImpl extends CommonDAOImpl<Employee, Long> implements EmployeeDAO {

    public EmployeeDAOImpl(){
        super(Employee.class);
    }


}
