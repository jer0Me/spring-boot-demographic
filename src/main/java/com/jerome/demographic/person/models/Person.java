package com.jerome.demographic.person.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Immutable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String ppsn;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String mobilePhone;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime created;

}
