package ru.java.project.TestDAO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.java.project.DAO.interfaces.AssignToProjectDAO;
import ru.java.project.DAO.interfaces.EmployeeDAO;
import ru.java.project.bd_classes.basic.AssignToProject;
import ru.java.project.bd_classes.basic.Employee;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class EmployeeDAOTest {
    @Autowired
    private EmployeeDAO employeeDAO;

    @Test
    void TestUpdate() {
        Employee employee = employeeDAO.getById(1L);
        assertNotNull(employee);
        employee.setEducation_degree("Магистр");
        employeeDAO.update(employee);
        employee = employeeDAO.getById(1L);
        assertEquals("Магистр", employee.getEducation_degree());
    }

    @Test
    void TestDelete() {
        Employee employee = employeeDAO.getById(1L);
        assertNotNull(employee);
        employeeDAO.delete(employee);
        employee = employeeDAO.getById(1L);
        assertNull(employee);
    }
}
