package ru.java.project.bd_classes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.java.project.bd_classes.basic.Template;

import java.sql.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PaymentHistory {
    private Long policy_id;

    private String policy_type;

    private Double payment;

    private Date paymet_date;
}
