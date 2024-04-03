package ru.java.project.bd_classes.basic;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Table(name = "payment_policy")
public class PaymentPolicy implements Template<Long> {
    @Getter
    public enum PolicyType {
        POSITION("По должности") ,
        ROLE("По роли в проекте") ,
        SPECIAL_OCCASION("По особому случаю");
        private String type;
        PolicyType (String s) { this.type = s; }
    }

    @Getter
    public enum Status {
        ACTIVE("Активно"),
        NON_ACTIVE("Не активно");
        private String stat;
        Status (String s) { this.stat = s; }
    }


    @Id
    @Column(name = "policy_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long id;

    @Column(name = "policy_type", nullable = false)
    @NonNull
    @Enumerated(EnumType.STRING)
    private PolicyType policy_type;

    @Column(name = "position")
    private String position;

    @Column(name = "project_id")
    private Long project_id;

    @Column(name = "project_role")
    private String project_role;

    @Column(name = "special_occasion")
    private String special_occasion;

    @Column(name = "payment", nullable = false)
    @NonNull
    private Double payment;

    @Column(name = "status", nullable = false)
    @NonNull
    @Enumerated(EnumType.STRING)
    private Status status;
}
