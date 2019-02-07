package com.jerome.demographic.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/persons")
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/list")
    public String findAll(Model model) {
        model.addAttribute("persons", personService.findAll());
        return "persons";
    }

    @GetMapping("add_person")
    public String addPersonForm(Model model) {
        model.addAttribute("personRequest", new PersonRequest());
        return "add_person";
    }

    @PostMapping("add_person")
    public RedirectView addPerson(@ModelAttribute PersonRequest personRequest) {
        personService.addNewPerson(personRequest);
        return new RedirectView("list");
    }
}
