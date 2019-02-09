package com.jerome.demographic.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/persons")
public class PersonController {

    public static final String REDIRECT_PERSONS_ADD_PERSON_VIEW_NAME = "redirect:/persons/add_person";
    public static final String REDIRECT_PERSONS_LIST_VIEW_NAME = "redirect:/persons/list";
    public static final String ERROR_MESSAGE_ATTRIBUTE_NAME = "errorMessage";

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("list")
    public ModelAndView findAll(ModelAndView modelAndView) {
        modelAndView.setViewName("persons");
        modelAndView.addObject("persons", personService.findAll());
        return modelAndView;
    }

    @GetMapping("add_person")
    public ModelAndView addPersonForm(ModelAndView modelAndView) {
        modelAndView.setViewName("add_person");
        modelAndView.addObject("personRequest", new PersonRequest());
        return modelAndView;
    }

    @PostMapping("add_person")
    public ModelAndView addPerson(@ModelAttribute PersonRequest personRequest,
                                  ModelAndView modelAndView,
                                  RedirectAttributes redirectAttributes) {
        try {
            personService.addNewPerson(personRequest);
            modelAndView.setViewName(REDIRECT_PERSONS_LIST_VIEW_NAME);
        } catch (PersonWithPpsnAlreadyAddedException e) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_ATTRIBUTE_NAME, e.getMessage());
            modelAndView.setViewName(REDIRECT_PERSONS_ADD_PERSON_VIEW_NAME);
        }

        return modelAndView;
    }
}
