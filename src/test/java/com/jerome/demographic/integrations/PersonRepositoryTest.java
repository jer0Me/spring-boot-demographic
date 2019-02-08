package com.jerome.demographic.integrations;

import com.jerome.demographic.person.Person;
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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        assertEquals(5, result.size());
        assertEquals("Althea Sweeney", result.get(0).getName());
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

        assertTrue(personSaved.isPresent());
        assertEquals(person.getName(), personSaved.get().getName());
        assertEquals(person.getPpsn(), personSaved.get().getPpsn());
        assertEquals(person.getDateOfBirth(), personSaved.get().getDateOfBirth());
        assertEquals(person.getMobilePhone(), personSaved.get().getMobilePhone());
        assertNotNull(personSaved.get().getCreated());
    }
}
