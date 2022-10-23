package com.example.volvo.web.dto;

import java.util.Objects;

public record ErrorResponseBody(String message) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponseBody that = (ErrorResponseBody) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public String toString() {
        return "ErrorResponseBody{" +
                "message='" + message + '\'' +
                '}';
    }
}
