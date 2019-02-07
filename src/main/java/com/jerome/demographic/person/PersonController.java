package com.jerome.demographic.person;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/persons")
public class PersonController {

    @GetMapping("/list")
    public String findAll() {
        return "persons";
    }

    @GetMapping("add_person")
    public String addPerson() {
        return "add_person";
    }
}
