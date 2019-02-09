package com.jerome.demographic;

import com.jerome.demographic.person.PersonWithPpsnAlreadyExistException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Lets catch every unexpected exceptions and return the user to a general error page.
     *
     * Every controller should take care of their own possible exceptions to return
     *
     * a proper error message to the user
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleEmployeeNotFoundException(HttpServletRequest request, Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error_page");
        return modelAndView;
    }

}