package com.example.volvo.domain.service;

import com.example.volvo.dao.CustomerAddressDAO;
import com.example.volvo.domain.Address;
import com.example.volvo.domain.validator.AddressValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CustomerAddressServiceTest {

    private CustomerAddressService customerAddressService;

    @Mock
    private CustomerAddressDAO customerAddressDAO;
    @Mock
    private AddressValidator addressValidator;


    @BeforeEach
    void setUp() {
        customerAddressService = new CustomerAddressService(
            addressValidator,
            customerAddressDAO
        );
    }


    @Test
    void shouldSuccessfullySave() {

        final var address = new Address(1L, "99999-999", 123);

        when(customerAddressDAO.save(address.getId(), address))
            .thenReturn(address);

        when(addressValidator.isZipCodeValid(address.getZipCode()))
            .thenReturn(true);

        final var response = customerAddressService.save(1L, address);

        assertEquals(address, response);
    }

    @Test
    void shouldSuccessfullyDelete() {

        final var customerId = 1L;
        final var address = new Address(1L, "99999-999", 123);

        when(customerAddressDAO.delete(customerId, address.getId()))
            .thenReturn(true);

        final var response = customerAddressService.delete(customerId, address.getId());

        assertTrue(response);
    }

}
