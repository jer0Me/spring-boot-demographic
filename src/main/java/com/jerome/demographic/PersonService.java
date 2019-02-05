package com.jerome.demographic;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<PersonDto> findAllByOrderByNameAsc() {
        return personRepository.findAll()
                .stream()
                .map(person -> new PersonDto(
                        person.getId(),
                        person.getName(),
                        person.getPpsn(),
                        person.getDateOfBirth(), person.getMobilePhone()
                )).collect(Collectors.toList());
    }

    public void addNewPerson(PersonRequest personRequest) {
        personRepository.save(
                mapPersonRequestToPerson(personRequest)
        );
    }

    private Person mapPersonRequestToPerson(PersonRequest personRequest) {
        Person person = new Person();
        person.setName(personRequest.getName());
        person.setPpsn(personRequest.getPpsn());
        person.setDateOfBirth(personRequest.getDateOfBirth());
        person.setMobilePhone(personRequest.getMobilePhone());
        return person;
    }
}
