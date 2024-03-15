package com.pweb.MyClinic.model.db;

import com.pweb.MyClinic.model.PersonalData;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import static org.hibernate.type.SqlTypes.JSON;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "employee_info")
public class EmployeeInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Integer userId;
    @JdbcTypeCode(JSON)
    private PersonalData personalData;
    private Integer baseSalary;
}