package ru.java.project.DAO.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.java.project.DAO.interfaces.AssignToProjectDAO;
import ru.java.project.DAO.interfaces.EmployeeDAO;
import ru.java.project.DAO.interfaces.ProjectDAO;
import ru.java.project.bd_classes.basic.AssignToProject;
import ru.java.project.bd_classes.EmployeesOnProject;
import ru.java.project.bd_classes.ProjectsOfEmployee;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AssignToProjectDAOImpl extends CommonDAOImpl<AssignToProject, Long> implements AssignToProjectDAO {
    public AssignToProjectDAOImpl() {
        super(AssignToProject.class);
    }

    @Autowired
    private EmployeeDAO employeeDAO = new EmployeeDAOImpl();

    @Autowired
    private ProjectDAO projectDAO = new ProjectDAOImpl();

    @Override
    public List<EmployeesOnProject> getEmployeesByProject(Long projectId) {
        List<EmployeesOnProject> ans = new ArrayList<>();

        for (AssignToProject assignToProject : getAll()) {
            if (assignToProject.getProject_id().equals(projectId)) {
                EmployeesOnProject tmp = new EmployeesOnProject();
                tmp.setId(assignToProject.getEmployee_id());
                tmp.setFull_name(employeeDAO.getById(assignToProject.getEmployee_id()).getFull_name());
                tmp.setProject_role(assignToProject.getProject_role());
                tmp.setStatus(assignToProject.getStatus());
                tmp.setStart_date(assignToProject.getStart_date());
                tmp.setFinish_date(assignToProject.getFinish_date());

                ans.add(tmp);
            }
        }
        return ans.isEmpty() ? null : ans;
    }

    @Override
    public List<ProjectsOfEmployee> getHistoryRolesAndProjects (Long employeeID) {
        List<ProjectsOfEmployee> ans = new ArrayList<>();

        for (AssignToProject assignToProject : getAll()) {
            if (assignToProject.getEmployee_id().equals(employeeID)) {
                ProjectsOfEmployee tmp = new ProjectsOfEmployee();
                tmp.setId(assignToProject.getProject_id());
                tmp.setProject_id(assignToProject.getProject_id());
                tmp.setProject_name(projectDAO.getById(assignToProject.getProject_id()).getProject_name());
                tmp.setProject_role(assignToProject.getProject_role());
                tmp.setStatus(assignToProject.getStatus());
                tmp.setStart_date(assignToProject.getStart_date());
                tmp.setFinish_date(assignToProject.getFinish_date());

                ans.add(tmp);
            }
        }
        return ans.isEmpty() ? null : ans;

    }

    @Override
    public List<ProjectsOfEmployee> getActualProjectByEmployee(Long employeeID){
        List<ProjectsOfEmployee> employee_projects = getHistoryRolesAndProjects(employeeID);
        List<ProjectsOfEmployee> ans = new ArrayList<>();
        if (employee_projects == null || employee_projects.isEmpty()) {
            return null;
        }
        for (ProjectsOfEmployee project : employee_projects) {
            if (project.getStatus().equals("ACTIVE")) {
                ans.add(project);
            }
        }

        return ans;
    }
}
