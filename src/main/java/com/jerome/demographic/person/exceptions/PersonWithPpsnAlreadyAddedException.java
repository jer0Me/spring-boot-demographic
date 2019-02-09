package com.jerome.demographic.person.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PersonWithPpsnAlreadyAddedException extends RuntimeException {

    public PersonWithPpsnAlreadyAddedException(String ppsn) {
        super("Person with PPSN: " + ppsn + " was already added to the system");
    }
}
