package com.jerome.demographic.person.models;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class PersonDto {

    private final Long id;
    private final String name;
    private final String ppsn;
    private final LocalDate dateOfBirth;
    private final String mobilePhone;
    private final LocalDateTime created;

}
