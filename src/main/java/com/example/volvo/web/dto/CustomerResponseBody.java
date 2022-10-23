package com.example.volvo.web.dto;

import com.example.volvo.domain.Customer;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public record CustomerResponseBody(Long documentId, String name, Integer age, LocalDateTime registrationDate, LocalDateTime lastUpdated, Set<AddressResponseBody> addresses) {

    public static CustomerResponseBody fromCustomer(Customer customer) {

        final var addresses = customer
            .getAddresses()
            .stream()
            .map(AddressResponseBody::fromAddress)
            .collect(Collectors.toSet());

        return new CustomerResponseBody(
            customer.getDocumentId(),
            customer.getName(),
            customer.getAge(),
            customer.getRegistrationDate(),
            customer.getLastUpdated(),
            addresses
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerResponseBody that = (CustomerResponseBody) o;
        return Objects.equals(documentId, that.documentId) && Objects.equals(name, that.name) && Objects.equals(age, that.age) && Objects.equals(registrationDate, that.registrationDate) && Objects.equals(lastUpdated, that.lastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId, name, age, registrationDate, lastUpdated);
    }

    @Override
    public String toString() {
        return "CustomerResponseBody{" +
                "documentId=" + documentId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", registrationDate=" + registrationDate +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
