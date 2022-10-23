package com.example.volvo.controller;

import com.example.volvo.web.dto.CustomerRequestBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerControllerIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    private CustomerRequestBody customerRequestBody;

    @BeforeAll
    void setUp() {

        customerRequestBody = new CustomerRequestBody(
            1L,
            "Foo",
            19
        );
    }


    @Test
    @Order(1)
    void findAllShouldReturnEmptyList() throws Exception {

        mvc
        .perform(get("/customer?zipCode=123"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Order(2)
    void saveSuccessfullySave() throws Exception {

        mvc
        .perform(post("/customer")
        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(customerRequestBody)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.documentId").exists())
        .andExpect(jsonPath("$.name", is("Foo")))
        .andExpect(jsonPath("$.age", is(19)))
                .andExpect(jsonPath("$.addresses", hasSize(0)));
    }

    @Test
    @Order(3)
    void findAllShouldReturnAllCustomers() throws Exception {

        mvc
        .perform(get("/customer"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @Order(4)
    void getCustomersByIdShouldReturnCustomer() throws Exception {

        mvc
        .perform(get("/customer/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.documentId").isNotEmpty())
        .andExpect(jsonPath("$.name", is("Foo")));
    }

    @Test
    @Order(5)
    void getCustomersByIdShouldReturnNotFound() throws Exception {

        final var nonExistentId = 7;

        mvc
        .perform(get("/customer/{id}", nonExistentId))
        .andExpect(status().isNotFound());
    }

    @Test
    @Order(6)
    void updateCustomerShouldUpdate() throws Exception {

        final var updatedCustomer = new CustomerRequestBody(
            1L,
            "Foo 1",
            20
        );

        mvc
        .perform(put("/customer/{id}", 1)
        .content(mapper.writeValueAsString(updatedCustomer))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.documentId").value("1"))
        .andExpect(jsonPath("$.name").value("Foo 1"))
        .andExpect(jsonPath("$.age").value("20"));
    }

    @Test
    @Order(7)
    void updateCustomerShouldReturnNotFound() throws Exception {

        final var updatedCustomer = new CustomerRequestBody(
            1L,
            "Foo 1",
            20
        );

        var nonExistentId = 7;

        mvc
        .perform(put("/customer/{id}", nonExistentId)
        .content(mapper.writeValueAsString(updatedCustomer))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    @Order(8)
    void deleteCustomerShouldDelete() throws Exception {

        mvc
        .perform(delete("/customer/{id}", 1))
        .andExpect(status().isNoContent());
    }

    @Test
    @Order(9)
    void deleteCustomerShouldReturnNotFound() throws Exception {

        final var nonExistentId = 7;

        mvc
        .perform(delete("/customer/{id}", nonExistentId))
        .andExpect(status().isNotFound());
    }
}
