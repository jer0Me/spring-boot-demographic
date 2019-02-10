package com.jerome.demographic.person.exceptions;

public class PersonRequestNotValidException extends RuntimeException {
    public PersonRequestNotValidException(String errorMessage) {
        super(errorMessage);
    }
}
