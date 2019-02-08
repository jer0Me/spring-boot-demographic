package com.jerome.demographic.units;

import com.jerome.demographic.person.PersonController;
import com.jerome.demographic.person.PersonDto;
import com.jerome.demographic.person.PersonRequest;
import com.jerome.demographic.person.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest {

    private PersonController personController;
    private PersonService mockPersonService;
    private ModelAndView mockModelAndView;
    private RedirectAttributes mockRedirectAttributes;

    @Before
    public void setUp() {
        mockPersonService = mock(PersonService.class);
        personController = new PersonController(mockPersonService);
        mockModelAndView = mock(ModelAndView.class);
        mockRedirectAttributes = mock(RedirectAttributes.class);
    }

    @Test
    public void shouldCallPersonServiceToRetrievePersonList() {
        personController.findAll(mockModelAndView);
        verify(mockPersonService).findAll();
    }

    @Test
    public void shouldReturnTheNameOfTheViewToShowTheListOfPersons() {
        assertThat(personController.findAll(new ModelAndView()).getViewName())
                .isEqualTo("persons");
    }

    @Test
    public void shouldPassThePersonsListToTheView() {
        List<PersonDto> emptyPersonList = Collections.emptyList();
        when(mockPersonService.findAll()).thenReturn(emptyPersonList);
        personController.findAll(mockModelAndView);
        verify(mockModelAndView).addObject("persons", emptyPersonList);
    }

    @Test
    public void shouldReturnTheNameOfTheViewToAccessToPersonForm() {
        assertThat(personController.addPersonForm(new ModelAndView()).getViewName())
                .isEqualTo("add_person");
    }

    @Test
    public void shouldPassNewInstanceOfPersonRequestToThePersonFormView() {
        personController.addPersonForm(mockModelAndView);
        verify(mockModelAndView).addObject("personRequest", new PersonRequest());
    }

    @Test
    public void shouldCallPersonServiceToAddPerson() {
        PersonRequest personRequest = new PersonRequest();
        personController.addPerson(personRequest, mockModelAndView, mockRedirectAttributes);
        verify(mockPersonService).addNewPerson(personRequest);
    }

    @Test
    public void shouldRedirectToListPageWhenPersonWasAddedSuccessfully() {
        ModelAndView modelAndView = new ModelAndView();
        ModelAndView modelAndViewRetrieved = personController.addPerson(new PersonRequest(), modelAndView, mockRedirectAttributes);
        assertThat(modelAndViewRetrieved.getViewName()).isEqualTo(PersonController.REDIRECT_PERSONS_LIST_VIEW_NAME);
    }
}
