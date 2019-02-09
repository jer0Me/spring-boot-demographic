package com.jerome.demographic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Lets catch every unexpected exceptions and return the user to a general error page.
     *
     * Every controller should take care of their own possible exceptions to return
     *
     * a proper error message to the user
     */

    @ExceptionHandler(Exception.class)
    public ModelAndView handleEmployeeNotFoundException(Exception ex) {
        LOGGER.error("There was an unexpected exception", ex);
        return new ModelAndView("error_page");
    }

}