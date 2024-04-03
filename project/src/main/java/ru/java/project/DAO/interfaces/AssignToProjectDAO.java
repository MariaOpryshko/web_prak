package ru.java.project.DAO.interfaces;

import ru.java.project.bd_classes.basic.AssignToProject;
import ru.java.project.bd_classes.EmployeesOnProject;
import ru.java.project.bd_classes.ProjectsOfEmployee;

import java.util.List;

public interface AssignToProjectDAO extends CommonDAO<AssignToProject, Long>{

    public List<EmployeesOnProject> getEmployeesByProject(Long projectId);


    public List<ProjectsOfEmployee> getHistoryRolesAndProjects (Long employeeID);

}
