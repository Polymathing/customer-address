package com.example.volvo.dao.repositories;

import com.example.volvo.dao.entities.AddressRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressRow, Long> {

    Optional<AddressRow> findByZipCodeAndNumber(String zipCode, Integer number);

}
