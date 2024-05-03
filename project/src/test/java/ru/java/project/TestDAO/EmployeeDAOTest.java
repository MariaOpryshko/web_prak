package ru.java.project.TestDAO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.java.project.DAO.interfaces.EmployeeDAO;
import ru.java.project.bd_classes.basic.Employee;


import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class EmployeeDAOTest {
    @Autowired
    private EmployeeDAO employeeDAO;

    @Test
    void TestUpdate() {
        Employee employee = employeeDAO.getById(2L);
        assertNotNull(employee);
        employee.setEducation_degree("Магистр");
        employeeDAO.update(employee);
        employee = employeeDAO.getById(2L);
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

    @Test
    void TestDeleteById() {
        Employee employee = employeeDAO.getById(3L);
        assertNotNull(employee);
        employeeDAO.deleteById(3L);
        employee = employeeDAO.getById(3L);
        assertNull(employee);
    }

    @Test
    void TestSave() {
        int len_before = employeeDAO.getAll().size();
        Employee tmp = new Employee("Иван Петрович", "дворник", Date.valueOf("1990-01-01"), "89280000000", 10);
        employeeDAO.save(tmp);
        assertEquals(len_before + 1, employeeDAO.getAll().size());
    }

    @Test
    void TestSaveCollection() {
        int len_before = employeeDAO.getAll().size();
        Employee tmp1 = new Employee("Иван Иваныч", "бухгалтер", Date.valueOf("1990-01-01"), "89280000000", 10);
        Employee tmp2 = new Employee("Иван Васильевич", "менеджер", Date.valueOf("1990-01-01"), "89280000000", 10);
        List<Employee> collection = new ArrayList<>();
        collection.add(tmp1);
        collection.add(tmp2);
        employeeDAO.saveCollection(collection);
        assertEquals(len_before + 2, employeeDAO.getAll().size());
    }


}
