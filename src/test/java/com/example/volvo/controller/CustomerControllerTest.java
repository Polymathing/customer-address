package com.example.volvo.controller;

import com.example.volvo.domain.Address;
import com.example.volvo.domain.Customer;
import com.example.volvo.domain.service.CustomerService;
import com.example.volvo.exceptions.ExistingRecordException;
import com.example.volvo.web.controller.CustomerController;
import com.example.volvo.web.dto.AddressResponseBody;
import com.example.volvo.web.dto.CustomerRequestBody;
import com.example.volvo.web.dto.CustomerResponseBody;
import com.example.volvo.web.dto.ErrorResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CustomerControllerTest {

    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    private CustomerResponseBody customerResponseBody;
    private CustomerRequestBody customerRequestBody;

    private Customer customer;
    private Set<Customer> customerSet;
    private Set<CustomerResponseBody> customerResponseBodySet;

    @BeforeEach
    void setup() {

        this.customerController = new CustomerController(customerService);

        final var addressHashSet = new HashSet<Address>();
        final var address = new Address(1L, "99999-999", 123);
        addressHashSet.add(address);

        final var addressResponseBodyHashSet = new HashSet<AddressResponseBody>();
        final var addressResponseBody = new AddressResponseBody(1L, "99999-999", 123);

        addressResponseBodyHashSet.add(addressResponseBody);

        customer = new Customer(
                1L,
                "Foo",
                19,
                LocalDateTime.now(),
                LocalDateTime.now(),
                addressHashSet
        );

        customerSet = new HashSet<Customer>();
        customerSet.add(customer);

        customerRequestBody = new CustomerRequestBody(
                customer.getDocumentId(),
                customer.getName(),
                customer.getAge()
        );

        customerResponseBody = new CustomerResponseBody(
            customer.getDocumentId(),
            customer.getName(),
            customer.getAge(),
            customer.getRegistrationDate(),
            customer.getLastUpdated(),
            addressResponseBodyHashSet
        );

        customerResponseBodySet = new HashSet<CustomerResponseBody>();
        customerResponseBodySet.add(customerResponseBody);
    }

    @Test
    void shouldReturnEmptySetWithStatusCode200WhenNoCustomerAdded() {

        when(customerService.findAll(null))
            .thenReturn(Collections.emptySet());

        final var response = customerController.findAll(null);

        assertThat(response.getStatusCode())
            .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
            .isEqualTo(Collections.emptySet());
    }

    @Test
    void shouldReturnEmptySetWithStatusCode200WhenZipCodeNotFound() {

        when(customerService.findAll(anyString()))
                .thenReturn(Collections.emptySet());

        final var response = customerController.findAll("12345-678");

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
                .isEqualTo(Collections.emptySet());
    }

    @Test
    void shouldReturnCustomerSetWithStatusCode200WhenZipCodeIsFound() {

        when(customerService.findAll(anyString()))
                .thenReturn(customerSet);

        final var response = customerController.findAll("99999-999");

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
                .isEqualTo(customerResponseBodySet);
    }

    @Test
    void shouldReturnAllCustomersWithStatusCode200() {

        when(customerService.findAll(null))
            .thenReturn(customerSet);

        final var response = customerController.findAll(null);

        assertThat(response.getStatusCode())
            .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
            .isEqualTo(customerResponseBodySet);
    }

    @Test
    void shouldReturnStatusCode404WhenIdWasNotFound() {

        when(customerService.findById(anyLong()))
            .thenReturn(Optional.empty());

        final var response = customerController.findById(1L);

        assertThat(response.getStatusCode())
            .isEqualTo(HttpStatus.NOT_FOUND);

        assertThat(response.getBody())
            .isNull();
    }


    @Test
    void shouldReturnStatusCode200WithCustomerDataWhenIdIsFound() {

        final var optionalCustomer = Optional.of(customer);

        when(customerService.findById(anyLong()))
            .thenReturn(optionalCustomer);

        final var response = customerController.findById(1L);

        assertThat(response.getStatusCode())
            .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
            .isEqualTo(customerResponseBody);
    }

    @Test
    void shouldCreateAndReturnStatusCode200WithCustomerDataInTheBody() {

        when(customerService.save(any(Customer.class)))
            .thenReturn(customer);

        final var response = customerController.create(customerRequestBody);

        assertThat(response.getStatusCode())
            .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
            .isEqualTo(customerResponseBody);
    }

    @Test
    void shouldThrowExistingRecordExceptionWithStatusCode400WhenIdAlreadyExists() {

        doThrow(ExistingRecordException.class)
            .when(customerService)
            .save(any(Customer.class));

        final var response = customerController.create(customerRequestBody);

        assertThat(response.getStatusCode())
            .isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(response.getBody())
            .isExactlyInstanceOf(ErrorResponseBody.class);
    }

    @Test
    void shouldUpdateAndReturnStatusCode200WithCustomerDataInTheBody() {

        final var optionalCustomer = Optional.of(customer);

        when(customerService.update(anyLong(), any(Customer.class)))
            .thenReturn(optionalCustomer);

        final var response = customerController.update(1L, customerRequestBody);

        assertThat(response.getStatusCode())
            .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
            .isEqualTo(customerResponseBody);
    }

    @Test
    void shouldReturnStatusCode404WhenNonExistentCustomerId() {

        when(customerService.update(anyLong(), any(Customer.class)))
            .thenReturn(Optional.empty());

        final var response = customerController.update(1L, customerRequestBody);

        assertThat(response.getStatusCode())
            .isEqualTo(HttpStatus.NOT_FOUND);

        assertThat(response.getBody())
            .isNull();
    }

    @Test
    void shouldReturnStatusCode404WhenDeletingNonExistentCustomerId() {

        when(customerService.delete(anyLong()))
            .thenReturn(false);

        final var response = customerController.delete(1L);

        assertThat(response.getStatusCode())
            .isEqualTo(HttpStatus.NOT_FOUND);

        assertThat(response.getBody())
            .isNull();
    }

    @Test
    void shouldReturnStatusCode204WhenDeleting() {

        when(customerService.delete(anyLong())).thenReturn(true);

        final var response = customerController.delete(1L);

        assertThat(response.getStatusCode())
            .isEqualTo(HttpStatus.NO_CONTENT);

        assertThat(response.getBody())
            .isNull();
    }
}
