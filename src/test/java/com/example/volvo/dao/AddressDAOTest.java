package com.example.volvo.dao;

import com.example.volvo.dao.AddressDAO;
import com.example.volvo.dao.entities.AddressRow;
import com.example.volvo.dao.entities.CustomerRow;
import com.example.volvo.dao.repositories.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class AddressDAOTest {

    private AddressDAO addressDAO;
    @Mock
    private AddressRepository addressRepository;
    private AddressRow mockAddressRow;

    @BeforeEach
    void setUp() {


        this.addressDAO = new AddressDAO(addressRepository);

        final var addressRowSet = new HashSet<AddressRow>();

        final var mockCustomerRow = new CustomerRow(
                1L,
                "Foo",
                19,
                LocalDateTime.now(),
                LocalDateTime.now(),
                addressRowSet
        );

        addressRowSet.add(mockAddressRow);

        final var mockCustomerRowSet = new HashSet<CustomerRow>();

        mockCustomerRowSet.add(mockCustomerRow);

        mockAddressRow = new AddressRow(1L, "99999-999", 123, mockCustomerRowSet);
    }

    @Test
    void ShouldReturnEmptyOptionalWhenZipCodeAndNumberCombinationDoesNotExist() {

        when(addressRepository.findByZipCodeAndNumber(anyString(), anyInt())).thenReturn(Optional.empty());

        final var response = addressDAO.findByZipCodeAndNumber("12345-678", 123);

        assertEquals(Optional.empty(), response);
    }

    @Test
    void ShouldReturnAddressWhenZipCodeAndNumberCombinationExists() {

        when(addressRepository.findByZipCodeAndNumber(anyString(), anyInt())).thenReturn(Optional.of(mockAddressRow));

        final var response = addressDAO.findByZipCodeAndNumber("12345-678", 123);

        final var optionalAddress = Optional.of(mockAddressRow.toAddress());

        assertEquals(optionalAddress, response);
    }


}
