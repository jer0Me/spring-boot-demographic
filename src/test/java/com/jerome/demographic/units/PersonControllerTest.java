package com.jerome.demographic.units;

import com.jerome.demographic.person.PersonController;
import com.jerome.demographic.person.PersonDto;
import com.jerome.demographic.person.PersonRequest;
import com.jerome.demographic.person.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest {

    private PersonController personController;
    private PersonService mockPersonService;
    private Model mockModel;

    @Before
    public void setUp() {
        mockPersonService = mock(PersonService.class);
        personController = new PersonController(mockPersonService);
        mockModel = mock(Model.class);
    }

    @Test
    public void shouldCallPersonServiceToRetrievePersonList() {
        personController.findAll(mockModel);
        verify(mockPersonService).findAll();
    }

    @Test
    public void shouldReturnTheNameOfTheViewToShowTheListOfPersons() {
        assertThat(personController.findAll(mockModel)).isEqualTo("persons");
    }

    @Test
    public void shouldPassThePersonsListToTheView() {
        List<PersonDto> emptyPersonList = Collections.emptyList();
        when(mockPersonService.findAll()).thenReturn(emptyPersonList);
        personController.findAll(mockModel);
        verify(mockModel).addAttribute("persons", emptyPersonList);
    }

    @Test
    public void shouldReturnTheNameOfTheViewToAccessToPersonForm() {
        assertThat(personController.addPersonForm(mockModel)).isEqualTo("add_person");
    }

    @Test
    public void shouldPassNewInstanceOfPersonRequestToThePersonFormView() {
        personController.addPersonForm(mockModel);
        verify(mockModel).addAttribute("personRequest", new PersonRequest());
    }

    @Test
    public void shouldCallPersonServiceToAddPerson() {
        PersonRequest personRequest = new PersonRequest();
        personController.addPerson(personRequest);
        verify(mockPersonService).addNewPerson(personRequest);
    }

    @Test
    public void shouldRedirectToListPageWhenPersonWasAddedSuccessfully() {
        RedirectView redirectView = personController.addPerson(new PersonRequest());
        assertThat(redirectView.getUrl()).isEqualTo("list");
    }
}
