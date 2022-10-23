package com.example.volvo.dao.entities;

import com.example.volvo.domain.Address;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "address")
public class AddressRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "number", nullable = false)
    private Integer number;

    @ManyToMany(mappedBy = "addressRows")
    private Set<CustomerRow> customerRows;

    public AddressRow() {}

    public AddressRow(Long id, String zipCode, Integer number, Set<CustomerRow> customerRows) {
        this.id = id;
        this.zipCode = zipCode;
        this.number = number;
        this.customerRows = customerRows;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public boolean addCustomerRecord(CustomerRow customerRow) {

        if (this.customerRows == null) {
            this.customerRows = new HashSet<>();
        }
        return this.customerRows.add(customerRow);
    }

    public boolean removeCustomerRecord(CustomerRow customerRow) {

        if (this.customerRows == null) {
            this.customerRows = new HashSet<>();
        }
        return this.customerRows.remove(customerRow);
    }

    public Set<CustomerRow> getCustomerRows() {
        return customerRows;
    }

    public static AddressRow toAddressRow(Address address) {

        return new AddressRow(
            address.getId(),
            address.getZipCode(),
            address.getNumber(),
            Collections.emptySet()
        );
    }

    public Address toAddress() {

        return new Address(
                id, this.zipCode,
            this.number
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressRow that = (AddressRow) o;
        return Objects.equals(id, that.id) && Objects.equals(zipCode, that.zipCode) && Objects.equals(number, that.number) && Objects.equals(customerRows, that.customerRows);
    }

    @Override
    public int hashCode() {
        return 13;
    }

    @Override
    public String toString() {
        return "AddressRow{" +
                "id=" + id +
                ", zipCode='" + zipCode + '\'' +
                ", number=" + number +
                ", customerRows=" + customerRows +
                '}';
    }
}
