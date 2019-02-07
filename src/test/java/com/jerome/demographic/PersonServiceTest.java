package com.jerome.demographic;

import com.jerome.demographic.person.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

    private PersonService personService;
    private PersonRepository mockPersonRepository;

    @Captor
    private ArgumentCaptor<Person> personArgumentCaptor;

    @Before
    public void setUp() {
        mockPersonRepository = mock(PersonRepository.class);
        personService = new PersonService(mockPersonRepository);
    }

    @Test
    public void shouldReturnAllPersonAvailableMappedToPersonDto() {
        Person person = Person.builder()
                .id(25L)
                .name("Robinette Deware")
                .ppsn("3131333L")
                .dateOfBirth(LocalDate.of(1987, 9, 1))
                .mobilePhone("016946584")
                .created(LocalDateTime.now())
                .build();
        when(mockPersonRepository.findAll()).thenReturn(Collections.singletonList(person));

        List<PersonDto> persons = personService.findAll();

        assertEquals(persons.size(), 1);
        assertEquals(person.getId(), persons.get(0).getId());
        assertEquals(person.getName(), persons.get(0).getName());
        assertEquals(person.getPpsn(), persons.get(0).getPpsn());
        assertEquals(person.getDateOfBirth(), persons.get(0).getDateOfBirth());
        assertEquals(person.getMobilePhone(), persons.get(0).getMobilePhone());
    }

    @Test
    public void shouldAddNewPersonToTheDatabase() {
        PersonRequest personRequest = new PersonRequest();
        personRequest.setName("Robinette Deware");
        personRequest.setPpsn("3131333L");
        personRequest.setDateOfBirth(LocalDate.of(1987, 9, 1));
        personRequest.setMobilePhone("016946584");

        personService.addNewPerson(personRequest);

        verify(mockPersonRepository).save(personArgumentCaptor.capture());

        assertNull(personArgumentCaptor.getValue().getId());
        assertNull(personArgumentCaptor.getValue().getCreated());
        assertEquals(personRequest.getName(), personArgumentCaptor.getValue().getName());
        assertEquals(personRequest.getPpsn(), personArgumentCaptor.getValue().getPpsn());
        assertEquals(personRequest.getDateOfBirth(), personArgumentCaptor.getValue().getDateOfBirth());
        assertEquals(personRequest.getMobilePhone(), personArgumentCaptor.getValue().getMobilePhone());

    }
}
