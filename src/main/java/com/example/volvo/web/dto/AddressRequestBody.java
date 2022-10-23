package com.example.volvo.web.dto;

import com.example.volvo.domain.Address;

import java.util.Objects;

public record AddressRequestBody(String zipCode, Integer number) {

    public Address toAddress() {

        return new Address(
            null,
            this.zipCode(),
            this.number()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressRequestBody that = (AddressRequestBody) o;
        return Objects.equals(zipCode, that.zipCode) && Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zipCode, number);
    }

    @Override
    public String toString() {
        return "AddressRequestBody{" +
                "zipCode='" + zipCode + '\'' +
                ", number=" + number +
                '}';
    }
}
