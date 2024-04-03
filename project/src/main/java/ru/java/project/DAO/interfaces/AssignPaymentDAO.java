package ru.java.project.DAO.interfaces;

import ru.java.project.bd_classes.PaymentHistory;
import ru.java.project.bd_classes.basic.AssignPayment;
import ru.java.project.bd_classes.PositionHistory;

import java.util.List;

public interface AssignPaymentDAO extends CommonDAO<AssignPayment, Long>{

    public List<PositionHistory> getPositionHistory (Long employeeId);

    public List<PaymentHistory> getPaymentHistory (Long employeeId);
}
