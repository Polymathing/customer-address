package com.example.volvo.dao;

import com.example.volvo.dao.entities.CustomerRow;
import com.example.volvo.dao.repositories.CustomerRepository;
import com.example.volvo.domain.Customer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomerDAO {

    private final CustomerRepository repository;

    public CustomerDAO(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Set<Customer> findAll(String zipCode) {

        final var customers = zipCode == null || zipCode.isEmpty() ?
                repository.findAll() :
                repository.findByZipCode(zipCode);

        return customers
            .stream()
            .map(CustomerRow::toCustomer)
            .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Optional<Customer> findById(Long id) {

        return repository
        .findById(id)
        .map(CustomerRow::toCustomer);
    }

    @Transactional
    public Customer save(Customer customer) {

        final var customerRecord = CustomerRow.toCustomerRow(customer);
        final var dbRecord = this.repository.save(customerRecord);

        return dbRecord.toCustomer();
    }

    @Transactional
    public Optional<Customer> update(Long id, Customer customer) {

        return repository.findById(id)
        .map(dbRecord -> {

            dbRecord.setAge(customer.getAge());
            dbRecord.setName(customer.getName());

            return dbRecord;
        })
        .map(CustomerRow::toCustomer);
    }

    @Transactional
    public boolean delete(Long id) {

        return repository.findById(id)
        .map(customerRow -> {

            repository.delete(customerRow);
            return true;

        })
        .orElse(false);
    }
}
