package com.jerome.demographic.units;

import com.jerome.demographic.person.*;
import com.jerome.demographic.person.exceptions.PersonWithPpsnAlreadyAddedException;
import com.jerome.demographic.person.models.Person;
import com.jerome.demographic.person.models.PersonDto;
import com.jerome.demographic.person.models.PersonRequest;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
                .name("Robinette Deware")
                .ppsn("3131333L")
                .dateOfBirth(LocalDate.of(1987, 9, 1))
                .mobilePhone("016946584")
                .created(LocalDateTime.now())
                .build();
        when(mockPersonRepository.findAll()).thenReturn(Collections.singletonList(person));

        List<PersonDto> persons = personService.findAll();

        assertThat(persons).hasSize(1);
        assertThat(person.getName()).isEqualTo(persons.get(0).getName());
        assertThat(person.getPpsn()).isEqualTo(persons.get(0).getPpsn());
        assertThat(person.getDateOfBirth()).isEqualTo(persons.get(0).getDateOfBirth());
        assertThat(person.getMobilePhone()).isEqualTo(persons.get(0).getMobilePhone());
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

        assertThat(personArgumentCaptor.getValue().getCreated()).isNull();
        assertThat(personRequest.getName()).isEqualTo(personArgumentCaptor.getValue().getName());
        assertThat(personRequest.getPpsn()).isEqualTo(personArgumentCaptor.getValue().getPpsn());
        assertThat(personRequest.getDateOfBirth()).isEqualTo(personArgumentCaptor.getValue().getDateOfBirth());
        assertThat(personRequest.getMobilePhone()).isEqualTo(personArgumentCaptor.getValue().getMobilePhone());
    }

    @Test(expected = PersonWithPpsnAlreadyAddedException.class)
    public void shouldThrowAnExceptionIfPersonWasAlreadyAdded() {
        when(mockPersonRepository.findByPpsn(any())).thenReturn(
                Optional.of(Person.builder().build())
        );
        personService.addNewPerson(new PersonRequest());
    }
}
