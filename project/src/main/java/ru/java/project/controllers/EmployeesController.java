package ru.java.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.java.project.DAO.implementation.*;
import ru.java.project.DAO.interfaces.*;
import ru.java.project.bd_classes.PaymentHistory;
import ru.java.project.bd_classes.PositionHistory;
import ru.java.project.bd_classes.ProjectsOfEmployee;
import ru.java.project.bd_classes.basic.AssignPayment;
import ru.java.project.bd_classes.basic.AssignToProject;
import ru.java.project.bd_classes.basic.Employee;
import ru.java.project.bd_classes.basic.PaymentPolicy;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.time.LocalDate;


@Controller
public class EmployeesController {
    @Autowired
    private final EmployeeDAO employeeDAO = new EmployeeDAOImpl();

    @Autowired
    private final PaymentPolicyDAO paymentPolicyDAO = new PaymentPolicyDAOImpl();

    @Autowired
    private final AssignToProjectDAO assignToProjectDAO = new AssignToProjectDAOImpl();

    @Autowired
    private final AssignPaymentDAO assignPaymentDAO = new AssignPaymentDAOImpl();

    @GetMapping("/list_of_employees")
    public String get_all_employees(Model model) {
        List<Employee> all_employees = (List<Employee>) employeeDAO.getAll();
        model.addAttribute("all_employees", all_employees);
        List<PaymentPolicy> all_payment_policies = (List<PaymentPolicy>) paymentPolicyDAO.getAll();
        List<PaymentPolicy> all_position_policies = new ArrayList<>();
        for (PaymentPolicy policy : all_payment_policies) {
            if (!(policy.getPosition() == null)) {
                all_position_policies.add(policy);

            }
        }
        model.addAttribute("all_position_policies", all_position_policies);
        return "employees";
    }

    @GetMapping("/filtered_list_of_employees")
    public String get_all_employees(Model model, @RequestParam(name = "position")String position) {
        if (position == null || position.isEmpty()) {
            return "redirect:/list_of_employees";
        }

        List<Employee> employees = (List<Employee>) employeeDAO.getAll();
        List<Employee> all_employees = new ArrayList<>();

        for (Employee employee : employees) {
            if (employee.getPosition().equals(position)) {
                all_employees.add(employee);
            }
        }
        model.addAttribute("all_employees", all_employees);

        List<PaymentPolicy> all_payment_policies = (List<PaymentPolicy>) paymentPolicyDAO.getAll();
        List<PaymentPolicy> all_position_policies = new ArrayList<>();

        for (PaymentPolicy policy : all_payment_policies) {
            if (!(policy.getPosition() == null)) {
                all_position_policies.add(policy);
            }
        }
        model.addAttribute("all_position_policies", all_position_policies);
        return "employees";
    }

    @GetMapping("/add-employee")
    public String getFormToAddEmployee (Model model) {
        List<PaymentPolicy> all_payment_policies = (List<PaymentPolicy>) paymentPolicyDAO.getAll();
        List<PaymentPolicy> all_position_policies = new ArrayList<>();

        for (PaymentPolicy policy : all_payment_policies) {
            if (!(policy.getPosition() == null)) {
                all_position_policies.add(policy);
            }
        }
        model.addAttribute("all_position_policies", all_position_policies);
        return "add-employee";
    }



    @PostMapping("/add-employee")
    public String addEmployee(@RequestParam(name = "name", required = false) String name,
                              @RequestParam(name = "position", required = false) String position,
                              @RequestParam(name = "address", required = false) String address,
                              @RequestParam(name = "dateOfBirth", required = false) String date_of_birth,
                              @RequestParam(name = "education", required = false) String education,
                              @RequestParam(name = "educationDegree", required = false) String education_degree,
                              @RequestParam(name = "phone", required = false) String phone,
                              @RequestParam(name = "workExp", required = false) Integer work_exp,
                              RedirectAttributes redirectAttributes) {
        // Сохранение новой политики выплат в базу данных
        System.out.println("name is " + name);
        if (name.isEmpty() || position.isEmpty() || date_of_birth == null || phone.isEmpty() || work_exp==null) {
            redirectAttributes.addFlashAttribute("error_msg", "Необходимые поля не заполнены!");
            return "redirect:/add-employee";
        }
        Employee newEmployee = new Employee();

        newEmployee.setFull_name(name);
        newEmployee.setPosition(position);
        newEmployee.setAddress_(address.equals("null")?null:address);
        newEmployee.setDate_of_birth(Date.valueOf(date_of_birth));
        newEmployee.setEducation(education.equals("null")?null:education);
        newEmployee.setEducation_degree(education_degree.equals("null")?null:education_degree);
        newEmployee.setPhone_number(phone);
        newEmployee.setWork_experience(work_exp);


        employeeDAO.save(newEmployee);

        AssignPayment assignPayment = new AssignPayment();

        List<Employee> getNewEmployee = employeeDAO.getEmployeeByName(name);
        Long id = 0L;
        for (Employee employee : getNewEmployee) {
            if (employee.getId() > id) {
                id = employee.getId();
            }
        }
        assignPayment.setEmployee_id(id);

        List<PaymentPolicy> policies = (List<PaymentPolicy>) paymentPolicyDAO.getAll();
        Long policy_id = 0L;
        for (PaymentPolicy policy: policies){
            System.out.println(policy.getStatus());
            System.out.println(policy.getPosition());
            if (policy.getStatus().equals("ACTIVE")
                    && policy.getPosition() != null
                    && policy.getPosition().equals(position)) {



                policy_id = policy.getId();
                break;
            }
        }
        assignPayment.setPolicy_id(policy_id);

        assignPayment.setPayment(paymentPolicyDAO.getById(policy_id).getPayment());
        assignPayment.setPayment_date(Date.valueOf(LocalDate.now()));

        assignPaymentDAO.save(assignPayment);
        System.out.println(8);
        // После сохранения перенаправляем пользователя на страницу со списком политик
        return "redirect:/list_of_employees";
    }



    @GetMapping("/employee-personal-page/{id}")
    public String getEmployeePage(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeDAO.getById(id);
        model.addAttribute("employee", employee);

        List<ProjectsOfEmployee> projects = assignToProjectDAO.getActualProjectByEmployee(id);
        if (projects == null || projects.isEmpty()) {
            System.out.println(0);
            model.addAttribute("projects", null);
        } else {
            model.addAttribute("projects", projects);
        }
        return "employee-personal-page";
    }

    @GetMapping("/project-history/{employee_id}")
    public String getProjectHistory(@PathVariable("employee_id") Long employee_id, Model model) {

        List<ProjectsOfEmployee> projects = assignToProjectDAO.getActualProjectByEmployee(employee_id);
        model.addAttribute("projects", projects);

        Employee employee = employeeDAO.getById(employee_id);
        model.addAttribute("employee", employee);

        return "project-history";
    }

    @GetMapping("/position-history/{employee_id}")
    public String getPositionHistory(@PathVariable("employee_id") Long employee_id, Model model) {

        List<PositionHistory> positionHistories = assignPaymentDAO.getPositionHistory(employee_id);

        model.addAttribute("positionHistories", positionHistories);

        Employee employee = employeeDAO.getById(employee_id);
        model.addAttribute("employee", employee);

        return "position-history";
    }

    @GetMapping("/payment-history/{employee_id}")
    public String getPaymentHistory(@PathVariable("employee_id") Long employee_id, Model model) {

        List<PaymentHistory> paymentHistories = assignPaymentDAO.getPaymentHistory(employee_id);

        model.addAttribute("payments", paymentHistories);

        Employee employee = employeeDAO.getById(employee_id);
        model.addAttribute("employee", employee);

        return "payment-history";
    }

    @GetMapping("/edit-employee/{id}")
    public String editEmployeeData(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeDAO.getById(id);
        model.addAttribute("employee", employee);
        List<ProjectsOfEmployee> projects = assignToProjectDAO.getActualProjectByEmployee(id);
        if (projects == null || projects.isEmpty()) {

            model.addAttribute("projects", null);
        } else {
            model.addAttribute("projects", projects);
        }
        List<PaymentPolicy> all_payment_policies = (List<PaymentPolicy>) paymentPolicyDAO.getAll();
        List<PaymentPolicy> all_position_policies = new ArrayList<>();

        for (PaymentPolicy policy : all_payment_policies) {
            if (!(policy.getPosition() == null)) {
                all_position_policies.add(policy);
            }
        }
        model.addAttribute("all_position_policies", all_position_policies);
        return "edit-employee";
    }


    @PostMapping("/update-employee/{id}")
    public String editPolicy(@PathVariable("id") Long id,
                             @RequestParam(name = "full_name", required = false) String name,
                             @RequestParam(name = "position", required = false) String position, //dont forget agg raw to table payment policy
                             @RequestParam(name = "address", required = false) String address,
                             @RequestParam(name = "date_of_birth", required = false) String date_of_birth,
                             @RequestParam(name = "education", required = false) String education,
                             @RequestParam(name = "education_degree", required = false) String education_degree,
                             @RequestParam(name = "phone", required = false) String phone,
                             @RequestParam(name = "work_exp", required = false) Integer work_exp,
                             RedirectAttributes redirectAttributes) {

        if (name == null || name.isEmpty() || position == null || position.isEmpty() ||
                date_of_birth == null || phone == null || phone.isEmpty() || work_exp==null) {
            System.out.println(3);
            redirectAttributes.addFlashAttribute("error_msg", "NOT NUll поля были не заполнены");
            return "redirect:/edit-employee/" + id.toString();
        }

        Employee updatedEmployee = employeeDAO.getById(id);

        updatedEmployee.setFull_name(name);
        updatedEmployee.setPosition(position);
        updatedEmployee.setAddress_(address.equals("null")?null:address);
        updatedEmployee.setDate_of_birth(Date.valueOf(date_of_birth));
        updatedEmployee.setEducation(education.equals("null")?null:education);
        updatedEmployee.setEducation_degree(education_degree.equals("null")?null:education_degree);
        updatedEmployee.setPhone_number(phone);
        updatedEmployee.setWork_experience(work_exp);


        employeeDAO.update(updatedEmployee);

        AssignPayment assignPayment = new AssignPayment();
        assignPayment.setEmployee_id(id);

        List<PaymentPolicy> policies = (List<PaymentPolicy>) paymentPolicyDAO.getAll();
        Long policy_id = 0L;
        for (PaymentPolicy policy: policies){
            if (policy.getStatus().equals("ACTIVE")
                    && policy.getPosition() != null
                    && policy.getPosition().equals(updatedEmployee.getPosition())) {
                policy_id = policy.getId();
                break;
            }
        }
        assignPayment.setPolicy_id(policy_id);

        assignPayment.setPayment(paymentPolicyDAO.getById(policy_id).getPayment());
        assignPayment.setPayment_date(Date.valueOf(LocalDate.now()));
        System.out.println(5);
        // После сохранения перенаправляем пользователя на страницу со списком политик
        return "redirect:/list_of_employees";
    }

    @PostMapping("/delete-employee/{id}")
    public String deletePolicy(@PathVariable("id") Long id,
                               RedirectAttributes redirectAttributes) {

        Employee employee = employeeDAO.getById(id);
        if (employee == null) {
            redirectAttributes.addFlashAttribute("error_msg", "Не существует сотрудника с номером " + id);
        } else {
            employeeDAO.delete(employee);
            redirectAttributes.addFlashAttribute("message", "Deleted successfully!");
        }

        return "redirect:/list_of_employees";
    }
}
