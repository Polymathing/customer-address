package com.example.volvo.exceptions;

public class InvalidZipCodeException extends RuntimeException {

    public InvalidZipCodeException(String zipCode) {
        super(String.format("Zip code '%s' is invalid. Format must be '99999-999'", zipCode));
    }
}
