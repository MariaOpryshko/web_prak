package ru.java.project.bd_classes.basic;

import lombok.*;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "assign_to_project")
public class AssignToProject implements Template<Long> {

    @Id
    @Column(name = "assignn_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    @NonNull
    private Long employee_id;

    @Column(name = "project_id", nullable = false)
    @NonNull
    private Long project_id;

    @Column(name = "project_role", nullable = false)
    @NonNull
    private String project_role;

    @Column(name = "assign_status", nullable = false)
    @NonNull
    private String status;

    @Column(name = "start_date", nullable = false)
    @NonNull
    private Date start_date;

    @Column(name = "finish_date")
    private Date finish_date;
}
