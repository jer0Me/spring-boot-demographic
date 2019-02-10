package com.jerome.demographic.person;

import com.jerome.demographic.common.Notification;
import com.jerome.demographic.person.exceptions.PersonRequestNotValidException;
import com.jerome.demographic.person.exceptions.PersonWithPpsnAlreadyAddedException;
import com.jerome.demographic.person.models.Person;
import com.jerome.demographic.person.models.PersonDto;
import com.jerome.demographic.person.models.PersonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional(readOnly = true)
    public List<PersonDto> findAll() {
        return personRepository.findAll()
                .stream()
                .map(person -> PersonDto.builder()
                        .name(person.getName())
                        .ppsn(person.getPpsn())
                        .dateOfBirth(person.getDateOfBirth())
                        .mobilePhone(person.getMobilePhone())
                        .build()
                ).collect(Collectors.toList());
    }

    @Transactional
    public void addNewPerson(PersonRequest personRequest) {
        checkIfThePersonWasAlreadyAdded(personRequest);
        validatePersonRequest(personRequest);
        personRepository.save(
                mapPersonRequestToPerson(personRequest)
        );
    }

    private void validatePersonRequest(PersonRequest personRequest) {
        Notification notification = new Notification();
        personRequest.validate(notification);

        if (notification.hasErrors()) {
            throw new PersonRequestNotValidException(notification.errorMessage());
        }
    }

    private void checkIfThePersonWasAlreadyAdded(PersonRequest personRequest) {
        if (personRepository.findByPpsn(personRequest.getPpsn()).isPresent()) {
            throw new PersonWithPpsnAlreadyAddedException(personRequest.getPpsn());
        }
    }

    private Person mapPersonRequestToPerson(PersonRequest personRequest) {
        return Person.builder()
                .name(personRequest.getName())
                .ppsn(personRequest.getPpsn())
                .dateOfBirth(personRequest.getDateOfBirth())
                .mobilePhone(personRequest.getMobilePhone())
                .build();
    }
}
