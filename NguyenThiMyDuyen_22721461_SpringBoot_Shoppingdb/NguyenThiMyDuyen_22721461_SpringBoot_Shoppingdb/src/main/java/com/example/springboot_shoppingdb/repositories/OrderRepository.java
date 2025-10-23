package com.example.springboot_shoppingdb.repositories;

import com.example.springboot_shoppingdb.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCustomerId(Integer customerId);

}
