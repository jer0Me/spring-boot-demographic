const personFormManager = (() => {

    const personFormManager = {};

    personFormManager.addPerson = () => {
        const validName = personFormManager.validateName();
        const validPpsn = personFormManager.validatePpsn();
        const validDateOfBirth = personFormManager.validateDateOfBirth();
        const validMobilePhone = personFormManager.validateMobilePhone();

        if (validName && validPpsn && validDateOfBirth && validMobilePhone) {
            $('#add_person_form').submit();
        }
    };

    personFormManager.validateName = () => {
        const nameInputId = 'name';
        const nameInputValue = $('#' + nameInputId).val();

        cleanInputStatus(nameInputId);

        if (validateIfInputIsEmpty(nameInputId)) {
            return false;
        }

        if (nameInputValue.length > 25) {
            markInputAsInvalid(nameInputId, 'Max 25 characters');
            return false;
        } else {
            markInputAsValid(nameInputId);
            return true;
        }
    };

    personFormManager.validatePpsn = () => {
        const ppsnInputId = 'ppsn';
        const ppsnInputValue = $('#' + ppsnInputId).val();

        cleanInputStatus(ppsnInputId);

        if (validateIfInputIsEmpty(ppsnInputId)) {
            return false;
        }

        if (!correctPpsnFormat(ppsnInputValue) || !correctChecksumCharacter(ppsnInputValue)) {
            markInputAsInvalid(ppsnInputId, 'The PPSN is not valid');
            return false;
        } else {
            markInputAsValid(ppsnInputId);
            return true;
        }
    };

    personFormManager.validateDateOfBirth = () => {
        const dateOfBirthId = 'date_of_birth';
        const dateOfBirthValue = $('#' + dateOfBirthId).val();

        cleanInputStatus(dateOfBirthId);

        if (validateIfInputIsEmpty(dateOfBirthId)) {
            return false;
        }

        const date = moment(dateOfBirthValue, 'DD/MM/YYYY', true);

        if (!date.isValid()) {
            markInputAsInvalid(dateOfBirthId, 'Date not valid. Hint: format should be like 01/01/2019');
            return false;
        }

        if (moment().diff(date, 'years') === 0) {
            markInputAsInvalid(dateOfBirthId, 'Cannot be future date');
            return false;
        }

        if (moment().diff(date, 'years') < 16) {
            markInputAsInvalid(dateOfBirthId, 'Must be over 16 years old');
            return false;
        } else {
            markInputAsValid(dateOfBirthId);
            return true;
        }
    };

    personFormManager.validateMobilePhone = () => {
        const mobilePhoneId = 'mobile_phone';
        const mobilePhoneValue = $('#' + mobilePhoneId).val();

        cleanInputStatus(mobilePhoneId);

        if (validateIfInputIsEmpty(mobilePhoneId)) {
            return false;
        }

        if (!mobilePhoneValue.startsWith('08')) {
            markInputAsInvalid(mobilePhoneId, 'Must begin with 08 prefix');
            return false;
        }

        if (!mobilePhoneValue.length < 9) {
            markInputAsInvalid(mobilePhoneId, 'Mobile phone not valid');
            return false;
        } else {
            markInputAsValid(mobilePhoneId);
            return true;
        }

    };

    function correctPpsnFormat(value) {
        return /^(\d{7})([A-Z]{1,2})$/i.test(value);
    }

    function correctChecksumCharacter(ppsn) {
        const ppsnChecksumCharacter = ppsn.substr(7);
        return calculatePpsnChecksumCharacter(ppsn) === ppsnChecksumCharacter;
    }

    function calculatePpsnChecksumCharacter(ppsn) {
        const numericPart = ppsn.substr(0, 7);

        let multiplyingFactor = 8;
        let sum = 0;

        for (let i = 0; i < numericPart.length; i++) {
            sum += numericPart[i] * multiplyingFactor--;
        }

        return String.fromCharCode((sum % 23) + 64);
    }

    function validateIfInputIsEmpty(inputId) {
        if ($('#' + inputId).val().length === 0) {
            markInputAsInvalid(inputId);
            showErrorInvalidFeedback(inputId, "It is required");
            return true;
        }
        return false;
    }

    function cleanInputStatus(inputId) {
        $('#' + inputId).removeClass('is-invalid is-valid');
        $('#' + inputId + '_feedback')
            .removeClass('invalid-feedback')
            .html('');
    }

    function markInputAsValid(inputId) {
        $('#' + inputId).addClass('is-valid');
    }

    function markInputAsInvalid(inputId, errorFeedback) {
        $('#' + inputId).addClass('is-invalid');
        showErrorInvalidFeedback(inputId, errorFeedback)
    }

    function showErrorInvalidFeedback(inputId, invalidFeedbackMessage) {
        $('#' + inputId + '_feedback')
            .html(invalidFeedbackMessage)
            .addClass('invalid-feedback');
    }

    return personFormManager;

})();
