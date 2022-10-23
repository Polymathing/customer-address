package com.example.volvo.web.controller;

import com.example.volvo.domain.service.CustomerService;
import com.example.volvo.exceptions.ExistingRecordException;
import com.example.volvo.web.dto.CustomerRequestBody;
import com.example.volvo.web.dto.CustomerResponseBody;
import com.example.volvo.web.dto.ErrorResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.example.volvo.web.dto.CustomerResponseBody.fromCustomer;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(name = "zipCode", required = false) String zipCode) {

        final var customers = service.findAll(zipCode)
            .stream()
            .map(CustomerResponseBody::fromCustomer)
            .collect(Collectors.toSet());

        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {

        final var customer = service
            .findById(id)
            .map(CustomerResponseBody::fromCustomer);

        return ResponseEntity.of(customer);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CustomerRequestBody body) {

        try {

            final var customer = service.save(body.toCustomer());
            return ResponseEntity.ok(fromCustomer(customer));
        }
        catch (ExistingRecordException e) {

            final var response = new ErrorResponseBody(e.getMessage());

            return ResponseEntity
            .badRequest()
            .body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody CustomerRequestBody body) {

        final var customer = service
            .update(id, body.toCustomer())
            .map(CustomerResponseBody::fromCustomer);

        return ResponseEntity.of(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        final var deleted = service.delete(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
