package ru.java.project.bd_classes.basic;

import lombok.*;

import jakarta.persistence.*;

import java.sql.Date;
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "employee")
public class Employee implements Template<Long> {

	@Id
	@Column(name = "employee_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "full_name", nullable = false)
	@NonNull
	private String full_name;

	@Column(name = "position", nullable = false)
	@NonNull
	private String position;

	@Column(name = "adress")
	private String address_;

	@Column(name = "date_of_birth", nullable = false)
	@NonNull
	private Date date_of_birth;

	@Column(name = "education")
	private String education;

	@Column(name = "education_degree")
	private String education_degree;

	@Column(name = "phone_number", nullable = false, length = 11)
	@NonNull
	private String phone_number;

	@Column(name = "work_experience", nullable = false)
	@NonNull
	private Integer work_experience;
}

