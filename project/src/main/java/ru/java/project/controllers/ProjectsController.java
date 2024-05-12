package ru.java.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.java.project.DAO.implementation.AssignToProjectDAOImpl;
import ru.java.project.DAO.implementation.EmployeeDAOImpl;
import ru.java.project.DAO.implementation.ProjectDAOImpl;
import ru.java.project.DAO.interfaces.AssignToProjectDAO;
import ru.java.project.DAO.interfaces.EmployeeDAO;
import ru.java.project.DAO.interfaces.ProjectDAO;
import ru.java.project.bd_classes.EmployeesOnProject;
import ru.java.project.bd_classes.ProjectsOfEmployee;
import ru.java.project.bd_classes.basic.AssignToProject;
import ru.java.project.bd_classes.basic.Employee;
import ru.java.project.bd_classes.basic.PaymentPolicy;
import ru.java.project.bd_classes.basic.Project;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectsController {
    @Autowired
    private final ProjectDAO projectDAO = new ProjectDAOImpl();

    @Autowired
    private final AssignToProjectDAO assignToProjectDAO = new AssignToProjectDAOImpl();

    @Autowired
    private final EmployeeDAO employeeDAO = new EmployeeDAOImpl();


    @GetMapping("/list_of_projects")
    public String get_all_projects(Model model) {
        List<Project> all_projects = (List<Project>) projectDAO.getAll();
        model.addAttribute("projects", all_projects);
        return "projects";
    }

    @GetMapping("/add-project")
    public String getFormToAddProject(Model model) {
        List<Project> projects = (List<Project>) projectDAO.getAll();
        model.addAttribute("projects", projects);
        return "add-project";
    }

    @PostMapping("/add-project")
    public String addProject(@RequestParam(name = "name", required = false) String name,
                             @RequestParam(name = "status", required = false) String status,
                             @RequestParam(name = "start_date", required = false) String start_date,
                             @RequestParam(name = "finish_date", required = false) String finish_date,
                             RedirectAttributes redirectAttributes) {
        if (name == null || name.isEmpty()
                || status == null || status.isEmpty()
                || start_date == null || start_date.isEmpty()) {
            redirectAttributes.addFlashAttribute("error_msg", "Необходимые поля не заполнены!");
            return "redirect:/add-project";
        }

        Project newProject = new Project();

        newProject.setProject_name(name);
        newProject.setStatus(status);
        newProject.setStart_date(Date.valueOf(start_date));
        if (!(finish_date == null || finish_date.isEmpty())) {
            newProject.setFinish_date(Date.valueOf(finish_date));
        }

        projectDAO.save(newProject);
        redirectAttributes.addFlashAttribute("message", "Новый проект добавлен");
        return "redirect:/list_of_projects";
    }

    @GetMapping("/project-personal-page/{id}")
    public String getProjectPage(@PathVariable("id") Long id, Model model) {
        Project project = projectDAO.getById(id);
        model.addAttribute("project", project);

        List<EmployeesOnProject> employees = assignToProjectDAO.getEmployeesByProject(id);
        if (employees == null || employees.isEmpty()) {
            model.addAttribute("employees", null);
        } else {
            model.addAttribute("employees", employees);
        }
        return "project-personal-page";
    }

    @GetMapping("/edit-project/{id}")
    public String editProjectPage(@PathVariable("id") Long id, Model model) {
        Project project = projectDAO.getById(id);
        model.addAttribute("project", project);

        List<EmployeesOnProject> employees = assignToProjectDAO.getEmployeesByProject(id);
        if (employees == null || employees.isEmpty()) {
            model.addAttribute("employees", null);
        } else {
            model.addAttribute("employees", employees);
        }
        return "edit-project";
    }

    @PostMapping("/update-project/{id}")
    public String editProject(@PathVariable("id") Long id,
                             @RequestParam(name = "name", required = false) String name,
                             @RequestParam(name = "status", required = false) String status,
                             @RequestParam(name = "finish_date", required = false) String finish_date,
                             RedirectAttributes redirectAttributes) {

        if (name == null || name.isEmpty() || status == null || status.isEmpty()) {
            redirectAttributes.addFlashAttribute("error_msg", "NOT NUll поля были не заполнены");
            return "redirect:/edit-project/" + id.toString();
        }

        Project updatedProject = projectDAO.getById(id);
        updatedProject.setProject_name(name);
        updatedProject.setStatus(status);
        updatedProject.setFinish_date(finish_date.isEmpty() ? null : Date.valueOf(finish_date));

        projectDAO.update(updatedProject);

        redirectAttributes.addFlashAttribute("message", "Проект был обновлен");

        return "redirect:/project-personal-page/" + id.toString();
    }

    @GetMapping("/add-project-employee/{project_id}")
    public String getFormOfProjectNewEmployee (@PathVariable("project_id") Long project_id,
                                               Model model,
                                               RedirectAttributes redirectAttributes) {
        model.addAttribute("projectId", project_id);

        List<Employee> employees = (List<Employee>) employeeDAO.getAll();
        model.addAttribute("employees", employees);

        return "add-project-employee";
    }

    @PostMapping("/add-project-employee/{project_id}")
    public String AddProjectNewEmployee (@PathVariable("project_id") Long project_id,
                                         @RequestParam(name = "employeeId", required = false) Long id,
                                         RedirectAttributes redirectAttributes) {
        Employee newEmp = employeeDAO.getById(id);
        AssignToProject newAssign = new AssignToProject();
        newAssign.setEmployee_id(id);
        newAssign.setProject_id(project_id);

        return "redirect:/project-personal-page/" + project_id.toString();
    }
    @PostMapping("/delete-project/{id}")
    public String deleteProject(@PathVariable("id") Long id,
                               RedirectAttributes redirectAttributes) {
        Project project = projectDAO.getById(id);

        if (project == null) {
            redirectAttributes.addFlashAttribute("error_msg", "Unable to delete project with ID = " + id);
        } else {
            projectDAO.delete(project);
            redirectAttributes.addFlashAttribute("message", "Deleted successfully!");
        }

        return "redirect:/list_of_projects";
    }
}
