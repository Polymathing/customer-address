package com.example.volvo.dao;

import com.example.volvo.dao.repositories.AddressRepository;
import com.example.volvo.dao.repositories.CustomerRepository;
import com.example.volvo.domain.Address;
import com.example.volvo.exceptions.RecordNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.example.volvo.dao.entities.AddressRow.toAddressRow;

@Component
public class CustomerAddressDAO {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    public CustomerAddressDAO(
            CustomerRepository customerRepository,
            AddressRepository addressRepository) {

        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public Address save(Long customerId, Address address) {

        final var zipCode = address.getZipCode();
        final var number = address.getNumber();

        final var addressRow = addressRepository
            .findByZipCodeAndNumber(zipCode, number)
            .orElseGet(() -> addressRepository.save(toAddressRow(address)));

        final var customerRow = customerRepository
            .findById(customerId)
            .orElseThrow(() -> new RecordNotFoundException("customer"));

        customerRow.addAddressRow(addressRow);

        return addressRow.toAddress();
    }

    @Transactional
    public boolean delete(Long customerId, Long addressId) {

        final var address = addressRepository.findById(addressId)
            .orElseThrow(() -> new RecordNotFoundException("address"));

        final var customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RecordNotFoundException("customer"));

        return customer.removeAddressRecord(address);
    }
}