package com.example.volvo.domain.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AddressValidatorTest {

    AddressValidator addressValidator;

    @BeforeEach
    void setup() {
        this.addressValidator = new AddressValidator();
    }

    @Test
    void shouldBeValidWhenPatternMatches() {

        final var isValid = addressValidator.isZipCodeValid("99999-999");
        assertTrue(isValid);
    }

    @Test
    void shouldBeInvalidWhenPatternDoesntMatch() {

        final var isValid = addressValidator.isZipCodeValid("9999-999");
        assertFalse(isValid);
    }
}