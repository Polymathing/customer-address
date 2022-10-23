package com.example.volvo.exceptions;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(String name) {
        super(String.format("A %s with the provided ID was not found", name));
    }
}
