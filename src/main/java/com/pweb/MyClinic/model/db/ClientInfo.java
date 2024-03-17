package com.pweb.MyClinic.model.db;

import com.pweb.MyClinic.model.PersonalData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import static jakarta.persistence.GenerationType.AUTO;
import static org.hibernate.type.SqlTypes.JSON;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "client_info")
public class ClientInfo {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private Integer userId;
    @JdbcTypeCode(JSON)
    private PersonalData personalData;
    private String creationDate;
}