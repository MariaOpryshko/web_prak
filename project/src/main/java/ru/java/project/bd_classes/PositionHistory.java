package ru.java.project.bd_classes;

import jakarta.persistence.Entity;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PositionHistory {

    private String position;

    private Double payment;

    private Date payment_date;

}
