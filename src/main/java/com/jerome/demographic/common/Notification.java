package com.jerome.demographic.common;

import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;

public class Notification {

    private List<String> errors;

    public Notification() {
        errors = new ArrayList<>();
    }

    public void addError(String message) {
        errors.add(message);
    }

    public String errorMessage() {
        if (errors.isEmpty())
            return Strings.EMPTY;

        return String.join("", errors);
    }

    public Boolean hasErrors() {
        return errors.size() > 0;
    }

}