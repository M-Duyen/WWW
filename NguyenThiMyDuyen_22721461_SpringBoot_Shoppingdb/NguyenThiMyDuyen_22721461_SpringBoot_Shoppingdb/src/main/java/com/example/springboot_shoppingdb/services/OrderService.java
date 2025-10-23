package com.example.springboot_shoppingdb.services;

import java.time.LocalDate;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot_shoppingdb.entities.Order;
import com.example.springboot_shoppingdb.entities.OrderLine;
import com.example.springboot_shoppingdb.entities.OrderLineId;
import com.example.springboot_shoppingdb.entities.Product;
import com.example.springboot_shoppingdb.entities.Customer;
import com.example.springboot_shoppingdb.repositories.OrderRepository;
import com.example.springboot_shoppingdb.repositories.ProductRepository;
import com.example.springboot_shoppingdb.repositories.CustomerRepository;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
            ProductRepository productRepository,
            CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    // create now accepts quantities map (productId -> qty)
    public Order create(Order order, List<Integer> productIds, Map<Integer, Integer> quantities, Integer customerId) {
        // set customer if provided
        if (customerId != null) {
            Customer customer = customerRepository.findById(customerId).orElse(null);
            order.setCustomer(customer);
        }
        // set date if not provided
        if (order.getDate() == null) {
            order.setDate(LocalDate.now());
        }
        // attach order lines using provided quantities
        if (order.getOrderLineSet() == null)
            order.setOrderLineSet(new HashSet<>());
        order.getOrderLineSet().clear();
        if (productIds != null && !productIds.isEmpty()) {
            List<Product> products = productRepository.findAllById(productIds);
            for (Product p : products) {
                int qty = Optional.ofNullable(quantities).map(m -> m.get(p.getId())).orElse(1);
                OrderLine ol = new OrderLine();
                ol.setProduct(p);
                ol.setQuantity(qty);
                ol.setOrder(order);
                // set embedded id (orderId left null, productId set)
                ol.setId(new OrderLineId(null, p.getId()));
                // set amount
                ol.setPrice(p.getPrice() * qty);
                order.getOrderLineSet().add(ol);
            }
        }
        // compute total
        recomputeTotal(order);
        return orderRepository.save(order);
    }

    // update now accepts quantities map
    public Order update(Integer id, Order order, List<Integer> productIds, Map<Integer, Integer> quantities,
            Integer customerId) {
        Order existing = findById(id);
        // copy simple props except id, orderLineSet, customer
        BeanUtils.copyProperties(order, existing, "id", "orderLineSet", "customer");

        // update customer if provided
        if (customerId != null) {
            Customer customer = customerRepository.findById(customerId).orElse(null);
            existing.setCustomer(customer);
        }
        // update date if provided on payload
        if (order.getDate() != null) {
            existing.setDate(order.getDate());
        }

        // --- safe diff: remove orderLines not in productIds, keep existing ones, add
        // missing ---
        Set<Integer> newProductIds = (productIds == null) ? Set.of() : productIds.stream().collect(Collectors.toSet());

        // map existing lines by productId
        Map<Integer, OrderLine> existingByProduct = existing.getOrderLineSet().stream()
                .filter(ol -> ol.getProduct() != null)
                .collect(Collectors.toMap(ol -> ol.getProduct().getId(), ol -> ol));

        // remove lines whose productId not in new set (use iterator remove to avoid
        // ConcurrentModification)
        existing.getOrderLineSet().removeIf(ol -> {
            Integer pid = (ol.getProduct() != null) ? ol.getProduct().getId() : null;
            return pid == null || !newProductIds.contains(pid);
        });

        // update quantities for existing lines
        if (quantities != null && !quantities.isEmpty()) {
            for (Map.Entry<Integer, Integer> e : quantities.entrySet()) {
                OrderLine ol = existingByProduct.get(e.getKey());
                if (ol != null) {
                    int qty = e.getValue() == null ? 1 : e.getValue();
                    ol.setQuantity(qty);
                    ol.setPrice(ol.getProduct().getPrice() * qty);
                }
            }
        }

        // add new lines for productIds that are not present
        if (productIds != null && !productIds.isEmpty()) {
            List<Product> products = productRepository.findAllById(productIds);
            for (Product p : products) {
                if (!existingByProduct.containsKey(p.getId())) {
                    int qty = Optional.ofNullable(quantities).map(m -> m.get(p.getId())).orElse(1);
                    OrderLine ol = new OrderLine();
                    ol.setProduct(p);
                    ol.setQuantity(qty);
                    ol.setOrder(existing);
                    // set embedded id with orderId (existing must have id)
                    ol.setId(new OrderLineId(existing.getId(), p.getId()));
                    ol.setPrice(p.getPrice() * qty);
                    existing.getOrderLineSet().add(ol);
                }
            }
        }

        // recompute total
        recomputeTotal(existing);
        return orderRepository.save(existing);
    }

    public void delete(Integer id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    private void recomputeTotal(Order order) {
        double total = 0.0;
        if (order.getOrderLineSet() != null) {
            for (OrderLine ol : order.getOrderLineSet()) {
                Product p = ol.getProduct();
                if (p != null)
                    total += p.getPrice() * ol.getQuantity();
            }
        }
        order.setTotal(total);
    }

    public List<Order> findByCustomerId(Integer customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
}
