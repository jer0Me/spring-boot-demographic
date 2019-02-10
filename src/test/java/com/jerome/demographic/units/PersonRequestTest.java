package com.jerome.demographic.units;

import com.jerome.demographic.common.Notification;
import com.jerome.demographic.person.models.PersonRequest;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.YEARS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

public class PersonRequestTest {

    private Notification mockNotification;
    private PersonRequest personRequest;

    @Before
    public void setUp() {
        mockNotification = mock(Notification.class);
        personRequest = new PersonRequest();
        personRequest.setName("Jerome");
        personRequest.setPpsn("1234567T");
        personRequest.setDateOfBirth(LocalDate.of(1992, 1, 1));
        personRequest.setMobilePhone("0834567890");
    }

    @Test
    public void shouldReturnAnErrorMessageWhenNameIsNull() {
        personRequest.setName(null);
        personRequest.validate(mockNotification);
        verify(mockNotification).addError(PersonRequest.NAME_IS_REQUIRED_ERROR_MESSAGE);
    }

    @Test
    public void shouldReturnAnErrorMessageWhenNameIsEmpty() {
        personRequest.setName("");
        personRequest.validate(mockNotification);
        verify(mockNotification).addError(PersonRequest.NAME_IS_REQUIRED_ERROR_MESSAGE);
    }

    @Test
    public void shouldReturnAnErrorMessageWhenNameSizeIsGreaterThan25Characters() {
        personRequest.setName("NameMoreThan25Characters!!");
        personRequest.validate(mockNotification);
        verify(mockNotification).addError(PersonRequest.NAME_MAX_25_CHARACTERS);
    }

    @Test
    public void shouldNotReturnAnyErrorIfNameIsCorrect() {
        personRequest.setName("Name");
        personRequest.validate(mockNotification);
        verify(mockNotification, never()).addError(PersonRequest.NAME_IS_REQUIRED_ERROR_MESSAGE);
    }

    @Test
    public void shouldReturnAnErrorMessageWhenPpsnIsNull() {
        personRequest.setPpsn(null);
        personRequest.validate(mockNotification);
        verify(mockNotification).addError(PersonRequest.PPSN_IS_REQUIRED_ERROR_MESSAGE);
    }

    @Test
    public void shouldReturnAnErrorMessageWhenPpsnIsEmpty() {
        personRequest.setPpsn("");
        personRequest.validate(mockNotification);
        verify(mockNotification).addError(PersonRequest.PPSN_IS_REQUIRED_ERROR_MESSAGE);
    }

    @Test
    public void shouldReturnAnErrorMessageIfPpsnDoesNotBeginWithDigits() {
        personRequest.setPpsn("asa");
        personRequest.validate(mockNotification);
        verify(mockNotification).addError(PersonRequest.PPSN_HAS_WRONG_FORMAT);
    }

    @Test
    public void shouldReturnAnErrorMessageIfPpsnDoesNotBeginWithSevenDigits() {
        personRequest.setPpsn("123456");
        personRequest.validate(mockNotification);
        verify(mockNotification).addError(PersonRequest.PPSN_HAS_WRONG_FORMAT);
    }

    @Test
    public void shouldReturnAnErrorMessageIfPpsnDoesNotEndWithAtLeastOneCharacter() {
        personRequest.setPpsn("1234567");
        personRequest.validate(mockNotification);
        verify(mockNotification).addError(PersonRequest.PPSN_HAS_WRONG_FORMAT);
    }

    @Test
    public void shouldReturnAnErrorMessageIfPpsnDoesNotEndWithMaximumTwoCharacters() {
        personRequest.setPpsn("1234567ABC");
        personRequest.setDateOfBirth(LocalDate.now());
        personRequest.validate(mockNotification);
        verify(mockNotification).addError(PersonRequest.PPSN_HAS_WRONG_FORMAT);
    }

    @Test
    public void shouldReturnAnErrorIfPpsnDoesNotHaveTheCorrectChecksumCharacterAtTheEnd() {
        personRequest.setPpsn("1234567L");
        personRequest.setDateOfBirth(LocalDate.now());
        personRequest.validate(mockNotification);
        verify(mockNotification).addError(PersonRequest.PPSN_HAS_WRONG_FORMAT);
    }

    @Test
    public void shouldNotReturnAnyErrorMessageIfPpsnIsCorrect() {
        personRequest.setName("1234567T");
        personRequest.setDateOfBirth(LocalDate.now());
        personRequest.validate(mockNotification);
        verify(mockNotification, never()).addError(PersonRequest.PPSN_HAS_WRONG_FORMAT);
    }

    @Test
    public void shouldReturnErrorMessageWhenDateOfBirthIsNull() {
        personRequest.setDateOfBirth(null);
        personRequest.validate(mockNotification);
        verify(mockNotification).addError(PersonRequest.DATE_OF_BIRTH_IS_REQUIRED_ERROR_MESSAGE);
    }

    @Test
    public void shouldReturnErrorMessageIfPersonIsNotOver16YearsOld() {
        personRequest.setDateOfBirth(LocalDate.of(2016, 1, 1));
        personRequest.validate(mockNotification);
        verify(mockNotification).addError(PersonRequest.PERSON_MUST_BE_OVER_16_YEARS_OLD_ERROR_MESSAGE);
    }

    @Test
    public void shouldReturnErrorMessageIfDateOfBirthIsAFutureDate() {
        personRequest.setDateOfBirth(LocalDate.now().plus(1, YEARS));
        personRequest.validate(mockNotification);
        verify(mockNotification).addError(PersonRequest.DATE_OF_BIRTH_CANNOT_BE_FUTURE_DATE_ERROR_MESSAGE);
    }

    @Test
    public void shouldNotReturnAnyErrorMessageIfDateOfBirthIsCorrect() {
        personRequest.validate(mockNotification);
        verify(mockNotification, never()).addError(PersonRequest.DATE_OF_BIRTH_IS_REQUIRED_ERROR_MESSAGE);
        verify(mockNotification, never()).addError(PersonRequest.PERSON_MUST_BE_OVER_16_YEARS_OLD_ERROR_MESSAGE);
    }

    @Test
    public void shouldReturnErrorMessageIfMobilePhoneIsNull() {
        personRequest.setMobilePhone(null);
        personRequest.validate(mockNotification);
        verify(mockNotification).addError(PersonRequest.MOBILE_PHONE_IS_REQUIRED_ERROR_MESSAGE);
    }

    @Test
    public void shouldReturnErrorMessageIfMobilePhoneIsEmpty() {
        personRequest.setMobilePhone("");
        personRequest.validate(mockNotification);
        verify(mockNotification).addError(PersonRequest.MOBILE_PHONE_IS_REQUIRED_ERROR_MESSAGE);
    }

    @Test
    public void shouldReturnErrorMessageIfMobilePhoneIsNotANumber() {
        personRequest.setMobilePhone("131AA");
        personRequest.validate(mockNotification);
        verify(mockNotification).addError(PersonRequest.MOBILE_PHONE_FORMAT_NOT_VALID_ERROR_MESSAGE);
    }

    @Test
    public void shouldReturnAnErrorMessageIfMobilePhoneIsLessThanNineDigits() {
        personRequest.setMobilePhone("0834567");
        personRequest.validate(mockNotification);
        verify(mockNotification).addError(PersonRequest.MOBILE_PHONE_FORMAT_NOT_VALID_ERROR_MESSAGE);
    }

    @Test
    public void shouldReturnAnErrorMessageIfMobilePhoneIsMoreThanNineDigits() {
        personRequest.setMobilePhone("12345678910");
        personRequest.validate(mockNotification);
        verify(mockNotification).addError(PersonRequest.MOBILE_PHONE_FORMAT_NOT_VALID_ERROR_MESSAGE);
    }

    @Test
    public void shouldReturnAnErrorMessageIfMobilePhoneDoesNotBeginWith08Prefix() {
        personRequest.setMobilePhone("123456789");
        personRequest.validate(mockNotification);
        verify(mockNotification).addError(PersonRequest.MOBILE_PHONE_MUST_BEGIN_WITH_08_PREFIX_ERROR_MESSAGE);
    }

    @Test
    public void shouldReturnAnErrorMessageIfMobilePhoneIsCorrect() {
        personRequest.setMobilePhone("083456789");
        personRequest.validate(mockNotification);
        verify(mockNotification, never()).addError(PersonRequest.MOBILE_PHONE_FORMAT_NOT_VALID_ERROR_MESSAGE);
        verify(mockNotification, never()).addError(PersonRequest.MOBILE_PHONE_MUST_BEGIN_WITH_08_PREFIX_ERROR_MESSAGE);
    }
}
