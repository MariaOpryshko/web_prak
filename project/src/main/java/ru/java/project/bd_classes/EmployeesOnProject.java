package ru.java.project.bd_classes;

import lombok.*;

import java.sql.Date;

import ru.java.project.bd_classes.basic.AssignToProject.Status;
import ru.java.project.bd_classes.basic.Template;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class EmployeesOnProject implements Template<Long> {

    private Long id;

    private String full_name;

    private String project_role;

    private Status status;

    private Date start_date;

    private Date finish_date;
}
