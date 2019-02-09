package com.jerome.demographic.integrations;

import com.jerome.demographic.person.models.Person;
import com.jerome.demographic.person.PersonRepository;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    @FlywayTest(invokeBaselineDB = true)
    @Transactional
    public void shouldRetrieveAllPersons() {
        List<Person> result = personRepository.findAll();
        assertThat(result).hasSize(5);
        assertThat(result.get(0).getName()).isEqualTo("Althea Sweeney");
    }

    @Test
    @FlywayTest(invokeBaselineDB = true)
    @Transactional
    public void shouldBeAbleToAddNewPerson() {
        Person person = Person.builder()
                .name("Robinette Deware")
                .ppsn("3131333L")
                .dateOfBirth(LocalDate.of(1987, 9, 1))
                .mobilePhone("016946584")
                .build();
        personRepository.save(person);
        Optional<Person> personSaved = personRepository.findById(person.getId());

        assertThat(personSaved).isPresent();
        assertThat(person.getName()).isEqualTo(personSaved.get().getName());
        assertThat(person.getPpsn()).isEqualTo(personSaved.get().getPpsn());
        assertThat(person.getDateOfBirth()).isEqualTo(personSaved.get().getDateOfBirth());
        assertThat(person.getMobilePhone()).isEqualTo(personSaved.get().getMobilePhone());
        assertThat(person.getCreated()).isNotNull();
    }
}
