package com.example.volvo.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class Customer {

    private final Long documentId;
    private final String name;
    private final Integer age;
    private final LocalDateTime registrationDate;
    private final LocalDateTime lastUpdated;
    private final Set<Address> addresses;

    public Customer(Long documentId, String name, Integer age, LocalDateTime registrationDate, LocalDateTime lastUpdated, Set<Address> addresses) {
        this.documentId = documentId;
        this.name = name;
        this.age = age;
        this.registrationDate = registrationDate;
        this.lastUpdated = lastUpdated;
        this.addresses = addresses;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(documentId, customer.documentId) && Objects.equals(name, customer.name) && Objects.equals(age, customer.age) && Objects.equals(registrationDate, customer.registrationDate) && Objects.equals(lastUpdated, customer.lastUpdated) && Objects.equals(addresses, customer.addresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId, name, age, registrationDate, lastUpdated, addresses);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "documentId=" + documentId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", registrationDate=" + registrationDate +
                ", lastUpdated=" + lastUpdated +
                ", addresses=" + addresses +
                '}';
    }
}
