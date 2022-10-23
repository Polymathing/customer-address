package com.example.volvo.dao.entities;

import com.example.volvo.domain.Customer;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "customer")
@EntityListeners(AuditingEntityListener.class)
public class CustomerRow {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @CreatedDate
    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @LastModifiedDate
    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    @ManyToMany
    @JoinTable(
        name = "customer_addresses",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "address_id"))
    private Set<AddressRow> addressRows;

    public CustomerRow() {}

    public CustomerRow(Long id, String name, Integer age, LocalDateTime registrationDate, LocalDateTime lastUpdated, Set<AddressRow> addressRows) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.registrationDate = registrationDate;
        this.lastUpdated = lastUpdated;
        this.addressRows = addressRows;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long documentId) {
        this.id = documentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdate) {
        this.lastUpdated = lastUpdate;
    }

    public boolean addAddressRow(AddressRow addressRow) {

        if (this.addressRows == null) {
            this.addressRows = new HashSet<>();
        }
        return this.addressRows.add(addressRow);
    }

    public boolean removeAddressRecord(AddressRow addressRow) {

        if (this.addressRows == null) {
            this.addressRows = new HashSet<>();
        }
        return this.addressRows.remove(addressRow);
    }

    public Set<AddressRow> getAddressRows() {
        return addressRows;
    }

    public static CustomerRow toCustomerRow(Customer customer) {

        final var addressRows = customer.getAddresses()
        .stream()
        .map(AddressRow::toAddressRow)
        .collect(Collectors.toSet());

        return new CustomerRow(
            customer.getDocumentId(),
            customer.getName(),
            customer.getAge(),
            customer.getRegistrationDate(),
            customer.getLastUpdated(),
            addressRows
        );
    }

    public Customer toCustomer() {

        final var addresses = this.getAddressRows()
        .stream()
        .map(AddressRow::toAddress)
        .collect(Collectors.toSet());

        return new Customer(
            this.getId(),
            this.getName(),
            this.getAge(),
            this.getRegistrationDate(),
            this.getLastUpdated(),
            addresses);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerRow that = (CustomerRow) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(age, that.age) && Objects.equals(registrationDate, that.registrationDate) && Objects.equals(lastUpdated, that.lastUpdated) && Objects.equals(addressRows, that.addressRows);
    }

    @Override
    public int hashCode() {
        return 13;
    }

    @Override
    public String toString() {
        return "CustomerRow{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", registrationDate=" + registrationDate +
                ", lastUpdate=" + lastUpdated +
                ", addressRows=" + addressRows +
                '}';
    }
}
