package ru.java.project.bd_classes.basic;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "assign_payment")
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class AssignPayment implements Template<Long> {

    @Id
    @Column(name = "assign_id")
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    @NonNull
    private Long employee_id;

    @Column(name = "policy_id", nullable = false)
    @NonNull
    private Long policy_id;

    @Column(name = "payment", nullable = false)
    @NonNull
    private Double payment;

    @Column(name = "payment_date", nullable = false)
    @NonNull
    private Date payment_date;
}