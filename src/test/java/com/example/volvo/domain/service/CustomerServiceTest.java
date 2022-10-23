package com.example.volvo.domain.service;

import com.example.volvo.dao.CustomerDAO;
import com.example.volvo.domain.Address;
import com.example.volvo.domain.Customer;
import com.example.volvo.exceptions.ExistingRecordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {

    private CustomerService customerService;

    @Mock
    private CustomerDAO customerDAO;
    private Customer mockCustomer;
    private Set<Customer> mockCustomerSet;

    @BeforeEach
    void setUp() {

        this.customerService = new CustomerService(customerDAO);
        final var addresses = new HashSet<Address>();

        final var mockAddress = new Address(1L, "99999-999", 123);
        addresses.add(mockAddress);

        mockCustomer = new Customer(
            1L,
            "Foo",
            19,
            LocalDateTime.now(),
            LocalDateTime.now(),
            addresses
        );

        mockCustomerSet = new HashSet<>();
        mockCustomerSet.add(mockCustomer);
    }

    @Test
    void shouldReturnEmptySetWhenNoCustomerWasAdded() {

        when(customerDAO.findAll(null))
            .thenReturn(Collections.emptySet());

        final var response = customerService.findAll(null);

        assertEquals(Collections.emptySet(), response);
    }

    @Test
    void shouldReturnASetOfCustomersWhenWhenAnyCustomerWasAdded() {

        when(customerDAO.findAll(null))
            .thenReturn(mockCustomerSet);

        final var response = customerService.findAll(null);

        assertEquals(mockCustomerSet, response);
    }

    @Test
    void shouldReturnEmptyOptionalWhenNoCustomerFoundById() {

        when(customerDAO.findById(anyLong()))
            .thenReturn(Optional.empty());

        final var response = customerService.findById(1L);

        assertEquals(Optional.empty(), response);
    }


    @Test
    void shouldReturnCustomerOptionalWhenCustomerFoundById() {

        final var optionalCustomer = Optional.of(mockCustomer);

        when(customerDAO.findById(anyLong()))
            .thenReturn(optionalCustomer);

        final var response = customerService.findById(1L);

        assertEquals(optionalCustomer, response);
    }

    @Test
    void shouldReturnEmptySetWhenNoCustomerFoundByZipCode() {

        when(customerDAO.findAll(anyString()))
                .thenReturn(Collections.emptySet());

        final var response = customerService.findAll("12345-678");

        assertEquals(Collections.emptySet(), response);
    }

    @Test
    void shouldReturnCustomerSetWhenCustomerFoundByZipCode() {

        when(customerDAO.findAll(anyString()))
                .thenReturn(mockCustomerSet);

        final var response = customerService.findAll("99999-999");

        assertEquals(mockCustomerSet, response);
    }

    @Test
    void shouldThrowExistingRecordExceptionWhenCustomerIdAlreadyExists() {

        final var optionalCustomer = Optional.of(mockCustomer);

        when(customerDAO.findById(anyLong()))
            .thenReturn(optionalCustomer);

        assertThatExceptionOfType(ExistingRecordException.class)
            .isThrownBy(() -> customerService.save(mockCustomer))
            .withMessage("A customer with the provided ID already exists");
    }

    @Test
    void shouldSuccessfullySaveWhenCustomerIdDoesNotExist() {

        when(customerDAO.save(any(Customer.class)))
            .thenReturn(mockCustomer);

        final var response = customerService.save(mockCustomer);

        assertEquals(mockCustomer, response);
    }

    @Test
    void shouldSuccessfullyUpdateWhenCustomerIdExists() {

        final var optionalCustomer = Optional.of(mockCustomer);

        when(customerDAO.update(anyLong(), any(Customer.class)))
            .thenReturn(optionalCustomer);

        final var response = customerService.update(1L, mockCustomer);

        assertEquals(optionalCustomer, response);
    }


    @Test
    void shouldSuccessfullyDeleteWhenCustomerIdExists() {

        when(customerDAO.delete(anyLong()))
            .thenReturn(true);

        final var response = customerService.delete(1L);

        assertTrue(response);
    }

    @Test
    void shouldNotDeleteWhenCustomerIdDoesNotExist() {

        when(customerDAO.delete(anyLong()))
            .thenReturn(false);

        final var response = customerService.delete(1L);

        assertFalse(response);
    }


}
