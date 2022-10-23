package com.example.volvo.dao;

import com.example.volvo.dao.CustomerDAO;
import com.example.volvo.dao.entities.CustomerRow;
import com.example.volvo.dao.repositories.CustomerRepository;
import com.example.volvo.domain.Address;
import com.example.volvo.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CustomerDAOTest {
    private CustomerDAO customerDAO;
    @Mock
    private CustomerRepository customerRepository;

    private Customer mockCustomer;
    private CustomerRow mockCustomerRow;
    private Address mockAddress;
    private ArrayList<CustomerRow> mockCustomerRowList;
    private Set<Customer> mockCustomerSet;

    @BeforeEach
    void setUp() {
        this.customerDAO = new CustomerDAO(customerRepository);

        final var addresses = new HashSet<Address>();

        mockAddress = new Address(1L, "99999-999", 123);
        addresses.add(mockAddress);


        mockCustomer = new Customer(
                1L,
                "Foo",
                19,
                LocalDateTime.now(),
                LocalDateTime.now(),
                addresses
        );

        mockCustomerRow = CustomerRow.toCustomerRow(mockCustomer);

        mockCustomerRowList = new ArrayList<CustomerRow>();
        mockCustomerRowList.add(mockCustomerRow);

        mockCustomerSet = new HashSet<Customer>();
        mockCustomerSet.add(mockCustomer);
    }

    @Test
    void shouldReturnEmptySetWhenNoCustomerWasAdded() {

        when(customerRepository.findAll()).thenReturn(Collections.emptyList());

        final var response = customerDAO.findAll(null);

        assertEquals(Collections.emptySet(), response);
    }

    @Test
    void shouldReturnASetOfCustomersWhenWhenAnyCustomerWasAdded() {

        doReturn(mockCustomerRowList).when(customerRepository).findAll();

        final var response = customerDAO.findAll(null);

        assertEquals(mockCustomerSet, response);
    }

    @Test
    void shouldReturnEmptyOptionalWhenNoCustomerFoundById() {

        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        final var response = customerDAO.findById(1L);

        assertEquals(Optional.empty(), response);
    }

    @Test
    void shouldReturnCustomerOptionalWhenCustomerFoundById() {

        final var optionalCustomerRow = Optional.of(mockCustomerRow);

        doReturn(optionalCustomerRow).when(customerRepository).findById(anyLong());

        final var optionalCustomer = Optional.of(mockCustomer);
        final var response = customerDAO.findById(1L);

        assertEquals(optionalCustomer, response);
    }

    @Test
    void shouldReturnEmptySetWhenNoCustomerIsNotFoundByZipCode() {

        when(customerRepository.findByZipCode(anyString())).thenReturn(Collections.emptyList());

        final var response = customerDAO.findAll("12345-678");

        assertEquals(Collections.emptySet(), response);
    }

    @Test
    void shouldReturnCustomerSetWhenCustomerFoundByZipCode() {

        when(customerRepository.findByZipCode(anyString())).thenReturn((mockCustomerRowList));

        final var response = customerDAO.findAll("99999-999");

        assertEquals(mockCustomerSet, response);
    }


    @Test
    void shouldSuccessfullySaveWhenCustomerIdDoesNotExist() {

        doReturn(mockCustomerRow).when(customerRepository).save(mockCustomerRow);

        final var response = customerDAO.save(mockCustomer);

        assertEquals(mockCustomer, response);
    }

    @Test
    void shouldSuccessfullyUpdateWhenCustomerIdExists() {

        final var optionalCustomerRow = Optional.of(mockCustomerRow);

        doReturn(optionalCustomerRow).when(customerRepository).findById(anyLong());

        final var optionalCustomer = Optional.of(mockCustomer);

        final var response = customerDAO.update(1L, mockCustomer);

        assertEquals(optionalCustomer, response);
    }

    @Test
    void shouldSuccessfullyDeleteWhenCustomerIdExists() {

        final var optionalCustomerRow = Optional.of(mockCustomerRow);

        doReturn(optionalCustomerRow).when(customerRepository).findById(anyLong());

        final var response = customerDAO.delete(1L);

        assertTrue(response);
    }

    @Test
    void shouldNotDeleteWhenCustomerIdDoesNotExist() {

        doReturn(Optional.empty()).when(customerRepository).findById(anyLong());

        final var response = customerDAO.delete(1L);

        assertFalse(response);
    }

}
