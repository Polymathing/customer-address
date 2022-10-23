package com.example.volvo.dao.repositories;

import com.example.volvo.dao.entities.CustomerRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerRow, Long> {

    @Query(
    "select c from customer c " +
    "inner join c.addressRows a " +
    "where a.zipCode = :zipCode")
    List<CustomerRow> findByZipCode(@Param("zipCode") String zipCode);
}
