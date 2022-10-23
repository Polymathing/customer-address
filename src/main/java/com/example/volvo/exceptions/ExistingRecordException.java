package com.example.volvo.exceptions;

public class ExistingRecordException extends RuntimeException{

    public ExistingRecordException(String name) {
        super(String.format("A %s with the provided ID already exists", name));
    }
}
