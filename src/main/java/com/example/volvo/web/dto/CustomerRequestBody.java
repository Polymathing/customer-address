package com.example.volvo.web.dto;

import com.example.volvo.domain.Customer;

import java.util.Collections;
import java.util.Objects;

public record CustomerRequestBody(Long documentId, String name, Integer age) {

    public Customer toCustomer() {

        return new Customer(
            this.documentId(),
            this.name(),
            this.age(),
            null,
            null,
            Collections.emptySet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerRequestBody that = (CustomerRequestBody) o;
        return Objects.equals(documentId, that.documentId) && Objects.equals(name, that.name) && Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId, name, age);
    }

    @Override
    public String toString() {
        return "CustomerRequestBody{" +
                "documentId=" + documentId +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
