package com.example.volvo.web.controller;

import com.example.volvo.domain.service.CustomerAddressService;
import com.example.volvo.exceptions.InvalidZipCodeException;
import com.example.volvo.exceptions.RecordNotFoundException;
import com.example.volvo.web.dto.AddressRequestBody;
import com.example.volvo.web.dto.ErrorResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.volvo.web.dto.AddressResponseBody.fromAddress;

@RestController
@RequestMapping("/customer/{customerId}/address")
public class CustomerAddressController {

    private final CustomerAddressService service;

    public CustomerAddressController(CustomerAddressService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@PathVariable("customerId") Long customerId, @RequestBody AddressRequestBody body) {

        try {
            final var address = service.save(customerId, body.toAddress());
            return ResponseEntity.ok(fromAddress(address));
        }
        catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch (InvalidZipCodeException e) {
            final var error = new ErrorResponseBody(e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<?> delete(@PathVariable("customerId") Long customerId, @PathVariable("addressId") Long addressId) {

        try {

            final var deleted = service.delete(customerId, addressId);

            if (!deleted) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
        }
        catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
