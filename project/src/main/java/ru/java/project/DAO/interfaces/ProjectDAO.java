package ru.java.project.DAO.interfaces;


import ru.java.project.bd_classes.basic.Project;

import java.util.List;

public interface ProjectDAO extends CommonDAO<Project, Long>{
    public List<Project> getProjectsByName(String name);
}
