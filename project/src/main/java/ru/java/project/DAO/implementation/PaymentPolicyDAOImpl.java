package ru.java.project.DAO.implementation;

import org.springframework.stereotype.Repository;
import ru.java.project.DAO.interfaces.PaymentPolicyDAO;
import ru.java.project.bd_classes.basic.PaymentPolicy;

@Repository
public class PaymentPolicyDAOImpl extends CommonDAOImpl<PaymentPolicy, Long> implements PaymentPolicyDAO {
    public PaymentPolicyDAOImpl() {
        super(PaymentPolicy.class);
    }

}
