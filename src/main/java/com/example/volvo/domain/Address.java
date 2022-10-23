package com.example.volvo.domain;

import java.util.Objects;

public class Address {

    private final Long id;
    private final String zipCode;
    private final Integer number;

    public Address(Long id, String zipCode, Integer number) {
        this.id = id;
        this.zipCode = zipCode;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Integer getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) && Objects.equals(zipCode, address.zipCode) && Objects.equals(number, address.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, zipCode, number);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", zipCode='" + zipCode + '\'' +
                ", number=" + number +
                '}';
    }
}
