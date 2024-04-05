package ru.java.project.bd_classes.basic;

import jakarta.persistence.*;
import lombok.*;


import java.sql.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "project")
public class Project implements Template<Long> {

    @Getter
    public enum Status {
        ACTIVE("Активно"),
        NON_ACTIVE("Не активно");
        private String stat;
        Status (String s) { this.stat = s; }
    }

    @Id
    @Column(name = "project_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_name", nullable = false)
    @NonNull
    private String project_name;

    @Column(name = "project_status", nullable = false)
    @NonNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "start_date", nullable = false)
    @NonNull
    private Date start_date;

    @Column(name = "finish_date")
    private Date finish_date;

}
