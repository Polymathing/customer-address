package com.example.volvo.domain.service;

import com.example.volvo.dao.CustomerAddressDAO;
import com.example.volvo.domain.Address;
import com.example.volvo.domain.validator.AddressValidator;
import com.example.volvo.exceptions.InvalidZipCodeException;
import org.springframework.stereotype.Service;

@Service
public class CustomerAddressService {

    private final AddressValidator addressValidator;
    private final CustomerAddressDAO customerAddressDAO;

    public CustomerAddressService(
            AddressValidator addressValidator,
            CustomerAddressDAO customerAddressDAO) {

        this.addressValidator = addressValidator;
        this.customerAddressDAO = customerAddressDAO;
    }

    public Address save(Long customerId, Address address) {

        final var zipCode = address.getZipCode();

        if (!addressValidator.isZipCodeValid(zipCode)) {
            throw new InvalidZipCodeException(zipCode);
        }

        return customerAddressDAO.save(customerId, address);
    }

    public boolean delete(Long customerId, Long addressId) {
        return customerAddressDAO.delete(customerId, addressId);
    }
}
