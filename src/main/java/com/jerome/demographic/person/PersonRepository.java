package com.jerome.demographic.person;

import com.jerome.demographic.person.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

    /**
     * Use this instead of using findById provided by JpaRepository in order to avoid confusion
     */
    Optional<Person> findByPpsn(String ppsn);

}
