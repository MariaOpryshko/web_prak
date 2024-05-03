package ru.java.project.DAO.interfaces;


import ru.java.project.bd_classes.basic.Employee;
import ru.java.project.bd_classes.basic.Project;

import java.util.List;

public interface  EmployeeDAO extends CommonDAO<Employee, Long> {

    public List<Employee> getEmployeeByName(String name);
}
