package com.example.volvo.web.dto;

import com.example.volvo.domain.Address;

import java.util.Objects;

public record AddressResponseBody(Long id, String zipCode, Integer number) {

    public static AddressResponseBody fromAddress(Address address) {

        return new AddressResponseBody(
            address.getId(),
            address.getZipCode(),
            address.getNumber()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressResponseBody that = (AddressResponseBody) o;
        return Objects.equals(id, that.id) && Objects.equals(zipCode, that.zipCode) && Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, zipCode, number);
    }

    @Override
    public String toString() {
        return "AddressResponseBody{" +
                "id=" + id +
                ", zipCode='" + zipCode + '\'' +
                ", number=" + number +
                '}';
    }
}
