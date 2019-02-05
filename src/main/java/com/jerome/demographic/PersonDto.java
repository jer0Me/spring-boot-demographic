package com.jerome.demographic;

import java.time.LocalDate;

public class PersonDto {

    private final Long id;
    private final String name;
    private final String ppsn;
    private final LocalDate dateOfBirth;
    private final String mobilePhone;

    public PersonDto(Long id, String name, String ppsn, LocalDate dateOfBirth, String mobilePhone) {
        this.id = id;
        this.name = name;
        this.ppsn = ppsn;
        this.dateOfBirth = dateOfBirth;
        this.mobilePhone = mobilePhone;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPpsn() {
        return ppsn;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

}
