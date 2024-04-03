package ru.java.project.DAO.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.java.project.DAO.interfaces.PaymentPolicyDAO;

import ru.java.project.bd_classes.PaymentHistory;
import ru.java.project.bd_classes.PositionHistory;
import ru.java.project.bd_classes.basic.AssignPayment;
import ru.java.project.DAO.interfaces.AssignPaymentDAO;


import java.util.ArrayList;
import java.util.List;

@Repository
public class AssignPaymentDAOImpl extends CommonDAOImpl<AssignPayment, Long> implements AssignPaymentDAO {

    public AssignPaymentDAOImpl() {
        super(AssignPayment.class);
    }

    @Autowired
    PaymentPolicyDAO paymentPolicyDAO = new PaymentPolicyDAOImpl();

    @Override
    public List<PositionHistory> getPositionHistory (Long employeeId) {
        List<PositionHistory> ans = new ArrayList<>();

        for (AssignPayment assignPaymentRaw : getAll()) {
            if (assignPaymentRaw.getEmployee_id().equals(employeeId)
                && !paymentPolicyDAO.getById(assignPaymentRaw.getPolicy_id()).getPosition().isEmpty()) {

                PositionHistory tmp = new PositionHistory();
                tmp.setPosition(paymentPolicyDAO.getById(assignPaymentRaw.getPolicy_id()).getPosition());
                tmp.setPayment(assignPaymentRaw.getPayment());
                tmp.setPayment_date(assignPaymentRaw.getPayment_date());

                ans.add(tmp);
            }
        }
        return ans.isEmpty() ? null : ans;
    }

    public List<PaymentHistory> getPaymentHistory (Long employeeId) {
        List<PaymentHistory> ans = new ArrayList<>();

        for (AssignPayment assignPaymentRaw : getAll()) {
            if (assignPaymentRaw.getEmployee_id().equals(employeeId)) {

                PaymentHistory tmp = new PaymentHistory();
                tmp.setPolicy_id(assignPaymentRaw.getPolicy_id());
                tmp.setPolicy_type(paymentPolicyDAO.getById(assignPaymentRaw.getPolicy_id()).getPolicy_type());
                tmp.setPayment(assignPaymentRaw.getPayment());
                tmp.setPaymet_date(assignPaymentRaw.getPayment_date());

                ans.add(tmp);
            }
        }
        return ans.isEmpty() ? null : ans;
    }

}
