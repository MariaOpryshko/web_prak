package ru.java.project.TestDAO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.java.project.DAO.interfaces.AssignToProjectDAO;
import ru.java.project.DAO.interfaces.ProjectDAO;
import ru.java.project.bd_classes.basic.Employee;
import ru.java.project.bd_classes.basic.Project;
import java.sql.Date;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.java.project.bd_classes.basic.Project.Status.NON_ACTIVE;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class ProjectDAOTest {
    @Autowired
    private ProjectDAO projectDAO;

//    public List<Project> getProjectsByName(String name)
    @Test
    void TestGetProjectsByName() {
        List<Project> ans = projectDAO.getProjectsByName("Создание");
        assertNotNull(ans);
    }



}
