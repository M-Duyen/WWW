package com.example.springboot_shoppingdb.repositories;

import com.example.springboot_shoppingdb.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    // find orders where customer name contains (case-insensitive)
    List<Order> findByCustomer_NameContainingIgnoreCase(String customerName);

    // find orders that have at least one order line whose product name contains
    // (case-insensitive)
    List<Order> findDistinctByOrderLineSet_Product_NameContainingIgnoreCase(String productName);

    // find orders by customer id (used to restrict customers to their own orders)
    List<Order> findByCustomer_Id(Integer customerId);
}
