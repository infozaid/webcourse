package com.infozaid.demo.customer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> getAllCustomers();
    Optional<Customer>  findCustomerById(Integer id);
}
