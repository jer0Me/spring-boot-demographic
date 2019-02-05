package com.jerome.demographic;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonRequest {

    private String name;
    private String ppsn;
    private LocalDate dateOfBirth;
    private String mobilePhone;

}
