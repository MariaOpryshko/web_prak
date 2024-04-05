package ru.java.project.TestDAO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.java.project.DAO.interfaces.AssignPaymentDAO;
import ru.java.project.DAO.interfaces.AssignToProjectDAO;
import ru.java.project.bd_classes.EmployeesOnProject;
import ru.java.project.bd_classes.PaymentHistory;
import ru.java.project.bd_classes.PositionHistory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class AssignPaymentDAOTest {

    @Autowired
    private AssignPaymentDAO assignPaymentDAO;


//    public List<PositionHistory> getPositionHistory (Long employeeId)
    @Test
    void TestGetPositionHistory() {
        List<PositionHistory> ans = assignPaymentDAO.getPositionHistory(7L);
        assertNotNull(ans);
    }

    //    public List<PaymentHistory> getPaymentHistory (Long employeeId);
    @Test
    void TestGetPaymentHistory() {
        List<PaymentHistory> ans = assignPaymentDAO.getPaymentHistory(7L);
        assertNotNull(ans);
    }
}
