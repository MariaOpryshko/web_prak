package ru.java.project.TestDAO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.java.project.DAO.interfaces.AssignToProjectDAO;
import ru.java.project.bd_classes.EmployeesOnProject;
import ru.java.project.bd_classes.ProjectsOfEmployee;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class AssignToProjectDAOTest {
    @Autowired
    private AssignToProjectDAO assignToProjectDAO;

    @Test
    void TestGetEmployeesByProject() {
        List<EmployeesOnProject> ans = assignToProjectDAO.getEmployeesByProject(1L);
        assertNotNull(ans);
    }

    @Test
    void TestGetHistoryRolesAndProjects() {
        List<ProjectsOfEmployee> ans = assignToProjectDAO.getHistoryRolesAndProjects(1L);
        assertNotNull(ans);
    }
}
