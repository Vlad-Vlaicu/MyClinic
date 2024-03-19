package com.pweb.MyClinic.model.db;

import com.pweb.MyClinic.model.PersonalData;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import static jakarta.persistence.GenerationType.TABLE;
import static org.hibernate.type.SqlTypes.JSON;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "employee_info")
public class EmployeeInfo {

    @Id
    @GeneratedValue(strategy = TABLE)
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    @JdbcTypeCode(JSON)
    @Column(name = "personal_data")
    private PersonalData personalData;

    @Column(name = "base_salary")
    private Integer baseSalary;

    @Column(name = "creation_date")
    private String creationDate;
}