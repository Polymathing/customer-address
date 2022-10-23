package com.example.volvo.dao;

import com.example.volvo.dao.entities.AddressRow;
import com.example.volvo.dao.repositories.AddressRepository;
import com.example.volvo.domain.Address;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AddressDAO {

    private final AddressRepository addressRepository;

    public AddressDAO(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Optional<Address> findByZipCodeAndNumber(String zipCode, Integer number) {

        return addressRepository
            .findByZipCodeAndNumber(zipCode, number)
            .map(AddressRow::toAddress);
    }
}