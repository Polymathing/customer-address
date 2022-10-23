package com.example.volvo.domain.service;

import com.example.volvo.dao.CustomerDAO;
import com.example.volvo.domain.Customer;
import com.example.volvo.exceptions.ExistingRecordException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public Set<Customer> findAll(String zipCode) {
        return customerDAO.findAll(zipCode);
    }

    public Optional<Customer> findById(Long id) {
        return customerDAO.findById(id);
    }

    public Customer save(Customer customer) {

        final var oCustomer = customerDAO.findById(customer.getDocumentId());

        if (oCustomer.isPresent()) {
            throw new ExistingRecordException("customer");
        }
        return customerDAO.save(customer);
    }

    public Optional<Customer> update(Long id, Customer customer) {
        return customerDAO.update(id, customer);
    }

    public boolean delete(Long id) {
        return customerDAO.delete(id);
    }
}
