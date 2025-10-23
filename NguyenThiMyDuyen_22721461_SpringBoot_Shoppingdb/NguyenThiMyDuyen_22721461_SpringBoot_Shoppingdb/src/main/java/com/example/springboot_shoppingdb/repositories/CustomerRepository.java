package com.example.springboot_shoppingdb.repositories;

import com.example.springboot_shoppingdb.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);
}
