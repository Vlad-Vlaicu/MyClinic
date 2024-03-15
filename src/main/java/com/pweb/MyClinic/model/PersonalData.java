package com.pweb.MyClinic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalData {
    private String name;
    private String phoneNumber;
    private String cnp;
    private String email;
    private String birthday;
}