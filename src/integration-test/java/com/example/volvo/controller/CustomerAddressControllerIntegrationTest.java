package com.example.volvo.controller;

import com.example.volvo.web.dto.AddressRequestBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerAddressControllerIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    private AddressRequestBody addressRequestBody;


    @BeforeAll
    void setUp() {

        addressRequestBody = new AddressRequestBody(
            "99999-999",
            123
        );
    }

    @Test
    @Order(1)
    void saveSuccessfullySave() throws Exception {

        mvc
        .perform(post("/customer/{customerId}/address", 123L)
        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(addressRequestBody)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.zipCode", is("99999-999")))
        .andExpect(jsonPath("$.number", is(123)));
    }

    @Test
    @Order(2)
    void saveThrowsBadRequestWhenZipCodeNotValid() throws Exception {

        final var body = new AddressRequestBody(
            "9999-999",
            123
        );

        mvc
        .perform(post("/customer/{customerId}/address", 123L)
        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(body)))
        .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    void deleteSuccessfully() throws Exception {

        mvc
        .perform(delete("/customer/{customerId}/address/{addressId}", 123L, 1L)
        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(addressRequestBody)))
        .andExpect(status().isNoContent());
    }

    @Test
    @Order(4)
    void deleteNoIdFoundReturns404() throws Exception {

        mvc
        .perform(delete("/customer/{customerId}/address/{addressId}", 123L, 1L)
        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(addressRequestBody)))
        .andExpect(status().isNotFound());
    }
}
