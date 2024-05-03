package ru.java.project.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.java.project.DAO.implementation.PaymentPolicyDAOImpl;
import ru.java.project.DAO.interfaces.PaymentPolicyDAO;
import ru.java.project.bd_classes.basic.Employee;
import ru.java.project.bd_classes.basic.PaymentPolicy;

import java.util.Date;
import java.util.List;

@Controller
public class PaymentPoliciesConroller {
    @Autowired
    private final PaymentPolicyDAO paymentPolicyDAO = new PaymentPolicyDAOImpl();

    @GetMapping("/list_of_payment_policies")
    public String get_all_payment_policies(Model model) {
        List<PaymentPolicy> all_payment_policies = (List<PaymentPolicy>) paymentPolicyDAO.getAll();
        model.addAttribute("all_payment_policies", all_payment_policies);
        return "payment-policies";
    }

    @GetMapping("/edit-policy/{id}")
    public String showEditPolicyForm(@PathVariable("id") Long id, Model model) {
        // Получаем политику выплат из базы данных по её идентификатору
        PaymentPolicy policy = paymentPolicyDAO.getById(id);
        // Помещаем политику выплат в модель данных, чтобы Thymeleaf мог использовать её в шаблоне
        model.addAttribute("policy", policy);
        return "edit-policy"; // Возвращаем имя шаблона Thymeleaf для отображения
    }

    @PostMapping("/edit-policy/{id}")
    public String editPolicy(@RequestParam(name="id") Long policy_id, @RequestParam(name="payment") Double payment,
                            RedirectAttributes redirectAttributes) {
        // Обновляем информацию о политике в базе данных
        PaymentPolicy updatedPolicy = paymentPolicyDAO.getById(policy_id);
        if (updatedPolicy == null) {
            redirectAttributes.addFlashAttribute("error_msg", "Unable to find payment policy with ID = " + policy_id);
            return "redirect:/list_of_payment_policies";
        }
        updatedPolicy.setPayment(payment);
        paymentPolicyDAO.update(updatedPolicy);
        // После обновления перенаправляем пользователя на страницу со списком политик
        return "redirect:/list_of_payment_policies";
    }

    @GetMapping("/add-policy")
    public String showAddPolicyForm() {
        return "add-policy";
    }

    @PostMapping("/add-policy")
    public String addEmployee(@RequestParam(name = "policyType") String type,
                              @RequestParam(name = "position", defaultValue="null") String position,
                              @RequestParam(name = "projectId", defaultValue="0") Long projectId,
                              @RequestParam(name = "projectRole", defaultValue="null") String projectRole,
                              @RequestParam(name = "specialOccasion", defaultValue="null") String specialOccasion,
                              @RequestParam(name = "amount") Double payment) {
        // Сохранение новой политики выплат в базу данных
//        PaymentPolicy newPolicy = new PaymentPolicy(type, position, projectId, projectRole, specialOccasion);
        PaymentPolicy newPolicy = new PaymentPolicy();
        newPolicy.setPolicy_type(type);

        if (!position.equals("null")) {
            System.out.println(projectId);
            newPolicy.setPosition(position);
        }

        if (projectId != 0){
//        if (!("" + projectId).equals("0")) {
            newPolicy.setProject_id(projectId);
        }

        if (!projectRole.equals("null")) {
            newPolicy.setProject_role(projectRole);
        }

        if (!specialOccasion.equals("null")) {
            newPolicy.setSpecial_occasion(specialOccasion);
        }

        newPolicy.setPayment(payment);
        newPolicy.setStatus("ACTIVE");

        System.out.println("policyType " + type);
        System.out.println("position " + position);
        System.out.println("projectId " + projectId);
        System.out.println("projectRole " + projectRole);
        System.out.println("specialOccasion " + specialOccasion);
        System.out.println("amount " + payment);

        paymentPolicyDAO.save(newPolicy);
        // После сохранения перенаправляем пользователя на страницу со списком политик
        return "redirect:/list_of_payment_policies";
    }

    @PostMapping("/delete-policy/{id}")
    public String deletePolicy(@PathVariable("id") Long id,
                               RedirectAttributes redirectAttributes) {
        System.out.println(1);
        PaymentPolicy paymentPolicy = paymentPolicyDAO.getById(id);
        System.out.println(0);
        if (paymentPolicy == null) {
            redirectAttributes.addFlashAttribute("error_msg", "Unable to delete payment policy with ID = " + id);
        } else {
            paymentPolicyDAO.delete(paymentPolicy);
            redirectAttributes.addFlashAttribute("message", "Deleted successfully!");
        }

        return "redirect:/list_of_payment_policies";
    }
}
