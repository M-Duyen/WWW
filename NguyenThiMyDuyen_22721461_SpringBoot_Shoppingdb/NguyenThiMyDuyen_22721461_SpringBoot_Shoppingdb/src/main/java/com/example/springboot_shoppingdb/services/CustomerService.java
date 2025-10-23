package com.example.springboot_shoppingdb.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot_shoppingdb.entities.Customer;
import com.example.springboot_shoppingdb.repositories.CustomerRepository;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Lấy tất cả khách hàng
     */
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    /**
     * Tìm khách hàng theo id, ném RuntimeException nếu không tồn tại
     */
    public Customer findById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    /**
     * Tạo mới khách hàng
     */
    public Customer create(Customer customer) {
        customer.setId(customer.getId()); // ensure new
        return customerRepository.save(customer);
    }

    /**
     * Cập nhật khách hàng theo id. Sao chép thuộc tính từ 'customer' sang thực thể
     * hiện có,
     * bỏ qua trường 'id'.
     */
    public Customer update(Integer id, Customer customer) {
        Customer existing = findById(id);
        BeanUtils.copyProperties(customer, existing, "id");
        return customerRepository.save(existing);
    }

    /**
     * Xóa khách hàng theo id
     */
    public void delete(Integer id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
}