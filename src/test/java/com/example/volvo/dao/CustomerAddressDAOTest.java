package com.example.volvo.dao;

import com.example.volvo.dao.CustomerAddressDAO;
import com.example.volvo.dao.entities.AddressRow;
import com.example.volvo.dao.entities.CustomerRow;
import com.example.volvo.dao.repositories.AddressRepository;
import com.example.volvo.dao.repositories.CustomerRepository;
import com.example.volvo.domain.Address;
import com.example.volvo.exceptions.RecordNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CustomerAddressDAOTest {

    private CustomerAddressDAO customerAddressDAO;

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AddressRepository addressRepository;

    private CustomerRow mockCustomerRow;
    private Set<CustomerRow> mockCustomerRowSet;
    private AddressRow mockAddressRow;

    private Address address;

    @BeforeEach
    void setUp() {

        this.customerAddressDAO = new CustomerAddressDAO(
            customerRepository,
            addressRepository
        );

        mockAddressRow = new AddressRow(
            1L,
            "99999-999",
            123,
            mockCustomerRowSet
        );

        final var addressRowSet = new HashSet<AddressRow>();
        addressRowSet.add(mockAddressRow);

        mockCustomerRow = new CustomerRow(
            1L,
            "Foo",
            19,
            LocalDateTime.now(),
            LocalDateTime.now(),
            addressRowSet
        );

        mockCustomerRowSet = new HashSet<>();
        mockCustomerRowSet.add(mockCustomerRow);

        address = mockAddressRow.toAddress();
    }

    @Test
    void shouldSuccessfullySaveCustomerAddress() {

        final var optionalCustomerRow= Optional.of(mockCustomerRow);
        final var optionalAddressRow = Optional.of(mockAddressRow);

        when(customerRepository.findById(anyLong())).thenReturn(optionalCustomerRow);
        when(addressRepository.findByZipCodeAndNumber(anyString(), anyInt())).thenReturn(optionalAddressRow);

        final var response = customerAddressDAO.save(1L, address);

        assertEquals(address, response);

    }

    @Test
    void shouldThrowRecordNotFoundExceptionWhenCustomerNotFound() {

        final var optionalAddressRow = Optional.of(mockAddressRow);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(addressRepository.findByZipCodeAndNumber(anyString(), anyInt())).thenReturn(optionalAddressRow);


        assertThatExceptionOfType(RecordNotFoundException.class)
                .isThrownBy(() -> customerAddressDAO.save(1L, address))
                .withMessage("A customer with the provided ID was not found" );

    }

    @Test
    void shouldSuccessfullyDeleteCustomerAddress() {

        final var optionalCustomerRow= Optional.of(mockCustomerRow);
        final var optionalAddressRow = Optional.of(mockAddressRow);

        when(customerRepository.findById(anyLong())).thenReturn(optionalCustomerRow);
        when(addressRepository.findById(anyLong())).thenReturn(optionalAddressRow);

        final var response = customerAddressDAO.delete(1L, 1L);

        assertTrue(response);

    }

    @Test
    void shouldThrowRecordNotFoundExceptionWhenCustomerIdDoesNotExist() {

        final var optionalAddressRow = Optional.of(mockAddressRow);
        when(addressRepository.findById(anyLong())).thenReturn(optionalAddressRow);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());


        assertThatExceptionOfType(RecordNotFoundException.class)
                .isThrownBy(() -> customerAddressDAO.delete(1L, 1L))
                .withMessage("A customer with the provided ID was not found" );

    }

    @Test
    void shouldThrowRecordNotFoundExceptionWhenAddressIdDoesNotExist() {

        final var optionalCustomerRow= Optional.of(mockCustomerRow);
        when(customerRepository.findById(anyLong())).thenReturn(optionalCustomerRow);

        when(addressRepository.findById(anyLong())).thenReturn(Optional.empty());


        assertThatExceptionOfType(RecordNotFoundException.class)
                .isThrownBy(() -> customerAddressDAO.delete(1L, 1L))
                .withMessage("A address with the provided ID was not found" );
    }
}
