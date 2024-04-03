package ru.java.project.DAO.implementation;


import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.java.project.bd_classes.basic.Project;
import ru.java.project.DAO.interfaces.ProjectDAO;

import java.util.List;

@Repository
public class ProjectDAOImpl  extends CommonDAOImpl<Project, Long> implements ProjectDAO {
    public ProjectDAOImpl() {
        super(Project.class);
    }
    @Override
    public List<Project> getProjectsByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Project> query = session.createQuery("FROM Project WHERE project_name LIKE :Name", Project.class)
                    .setParameter("Name", "%" + name + "%");
            return query.getResultList().isEmpty() ? null : query.getResultList();
        }
    }

}
