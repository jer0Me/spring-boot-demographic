package com.jerome.demographic.person;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PersonWithPpsnAlreadyExistException extends RuntimeException {

    public PersonWithPpsnAlreadyExistException(String ppsn) {
        super("There already exists a Person with PPSN: " + ppsn);
    }
}
