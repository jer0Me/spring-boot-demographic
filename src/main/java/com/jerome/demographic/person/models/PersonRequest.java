package com.jerome.demographic.person.models;

import com.jerome.demographic.common.Notification;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.YEARS;

@Data
public class PersonRequest {

    public static final String NAME_IS_REQUIRED_ERROR_MESSAGE = "Name is required";
    public static final String PPSN_IS_REQUIRED_ERROR_MESSAGE = "PPSN is required";
    public static final String PPSN_HAS_WRONG_FORMAT = "PPSN has a wrong format";
    public static final String PERSON_MUST_BE_OVER_16_YEARS_OLD_ERROR_MESSAGE = "Person must be over 16 years old";
    public static final String DATE_OF_BIRTH_IS_REQUIRED_ERROR_MESSAGE = "Date of birth is required";
    public static final String DATE_OF_BIRTH_CANNOT_BE_FUTURE_DATE_ERROR_MESSAGE = "Date of birth cannot be future date";
    public static final String MOBILE_PHONE_IS_REQUIRED_ERROR_MESSAGE = "Mobile phone is required";
    public static final String MOBILE_PHONE_MUST_BEGIN_WITH_08_PREFIX_ERROR_MESSAGE = "Mobile phone must begin with 08";
    public static final String MOBILE_PHONE_FORMAT_NOT_VALID_ERROR_MESSAGE = "Mobile phone format not valid";

    private String name;
    private String ppsn;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    private String mobilePhone;

    private Notification notification;

    public void validate(Notification notification) {

        this.notification = notification;
        validateName(notification);
        validatePpsn();
        validateDateOfBirth();
        validateMobilePhone();
    }

    private void validateName(Notification notification) {
        if (name == null || name.isEmpty()) {
            notification.addError(NAME_IS_REQUIRED_ERROR_MESSAGE);
        }
    }

    private void validatePpsn() {
        if (ppsn == null || ppsn.isEmpty()) {
            notification.addError(PPSN_IS_REQUIRED_ERROR_MESSAGE);
        } else if(!ppsn.matches("\\d{7}[A-Z]{1,2}")) {
            notification.addError(PPSN_HAS_WRONG_FORMAT);
        } else {
            validatePpsnCharacterChecksum();
        }

    }

    private void validatePpsnCharacterChecksum() {
        int numberToSumToMatchAsciiTable = 63;

        String ppsnNumberPart = ppsn.substring(0, 7);
        String checkSumCharacter = ppsn.substring(7, 8);

        int sum = 0;
        int multiplyFactor = 8;

        for (Character character : ppsnNumberPart.toCharArray()) {
            sum += character.hashCode() * multiplyFactor--;
        }

        int checkSum = ((sum % 23) + numberToSumToMatchAsciiTable);

        if (checkSumCharacter.hashCode() != checkSum) {
            notification.addError(PPSN_HAS_WRONG_FORMAT);
        }
    }

    private void validateDateOfBirth() {
        if (dateOfBirth == null) {
            notification.addError(DATE_OF_BIRTH_IS_REQUIRED_ERROR_MESSAGE);
        } else if (DAYS.between(LocalDate.now(), dateOfBirth) >= 0){
            notification.addError(DATE_OF_BIRTH_CANNOT_BE_FUTURE_DATE_ERROR_MESSAGE);
        } else if (YEARS.between(dateOfBirth, LocalDate.now()) < 16){
            notification.addError(PERSON_MUST_BE_OVER_16_YEARS_OLD_ERROR_MESSAGE);
        }
    }

    private void validateMobilePhone() {
        if (mobilePhone == null || mobilePhone.isEmpty()) {
            notification.addError(MOBILE_PHONE_IS_REQUIRED_ERROR_MESSAGE);
        } else {
            validateMobilePhoneFormat();
        }
    }

    private void validateMobilePhoneFormat() {
        if (!mobilePhone.matches("\\d{9}")) {
            notification.addError(MOBILE_PHONE_FORMAT_NOT_VALID_ERROR_MESSAGE);
        } else if (!mobilePhone.startsWith("08")) {
            notification.addError(MOBILE_PHONE_MUST_BEGIN_WITH_08_PREFIX_ERROR_MESSAGE);
        }
    }

}
