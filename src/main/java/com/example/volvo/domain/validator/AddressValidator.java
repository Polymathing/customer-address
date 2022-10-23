package com.example.volvo.domain.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class AddressValidator {

    public static final Pattern ZIPCODE_PATTERN = Pattern.compile("^\\d{5}-\\d{3}$");

    public boolean isZipCodeValid(final String input) {

        return ZIPCODE_PATTERN
            .matcher(input)
            .matches();
    }
}
