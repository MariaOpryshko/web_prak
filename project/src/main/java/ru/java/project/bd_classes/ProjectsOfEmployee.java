package ru.java.project.bd_classes;

import lombok.*;

import java.sql.Date;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProjectsOfEmployee {

    private Long id;

    private Long project_id;

    private String project_name;

    private String project_role;

    private String status;

    private Date start_date;

    private Date finish_date;
}

