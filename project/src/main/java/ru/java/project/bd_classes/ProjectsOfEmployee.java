package ru.java.project.bd_classes;

import lombok.*;

import java.sql.Date;

import ru.java.project.bd_classes.basic.AssignToProject;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProjectsOfEmployee {

    private Long id;

    private String project_name;

    private String project_role;

    private AssignToProject.Status status;

    private Date start_date;

    private Date finish_date;
}

