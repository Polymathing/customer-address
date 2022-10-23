package com.example.volvo.controller;

import com.example.volvo.domain.Address;
import com.example.volvo.domain.service.CustomerAddressService;
import com.example.volvo.exceptions.RecordNotFoundException;
import com.example.volvo.web.controller.CustomerAddressController;
import com.example.volvo.web.dto.AddressRequestBody;
import com.example.volvo.web.dto.AddressResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CustomerAddressControllerTest {

    private CustomerAddressController customerAddressController;

    @Mock
    private CustomerAddressService customerAddressService;

    private AddressRequestBody addressRequestBody;
    private AddressResponseBody addressResponseBody;
    private Address address;

    @BeforeEach
    void setUp() {

        customerAddressController = new CustomerAddressController(customerAddressService);

        addressRequestBody = new AddressRequestBody("99999-999", 123);

        addressResponseBody = new AddressResponseBody(
                1L,
                "99999-999",
                123
        );

        address = new Address(
                1L,
                "99999-999",
                123
        );
    }

    @Test
    void shouldSuccessfullySaveAddressStatusCode200() {

        when(customerAddressService.save(anyLong(), any(Address.class)))
                .thenReturn(address);

        final var response = customerAddressController.create(1L, addressRequestBody);

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
                .isEqualTo(addressResponseBody);
    }

    @Test
    void shouldWhenSavingCustomerIdNonExistentReturns404() {

        doThrow(RecordNotFoundException.class)
                .when(customerAddressService).save(anyLong(), any(Address.class));


        final var response = customerAddressController.create(1L, addressRequestBody);

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        assertThat(response.getBody())
                .isNull();
    }

    @Test
    void shouldSuccessfullyDeleteWithStatusCode204() {

        when(customerAddressService.delete(anyLong(), anyLong()))
                .thenReturn(true);

        final var response = customerAddressController.delete(1L, 1L);

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);

        assertThat(response.getBody())
                .isNull();

    }

    @Test
    void shouldWhenDeletingAddressOrCustomerIdNonExistentReturn404() {

        doThrow(RecordNotFoundException.class)
                .when(customerAddressService).delete(anyLong(), anyLong());


        final var response = customerAddressController.delete(1L, 1L);

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        assertThat(response.getBody())
                .isNull();

    }
}
