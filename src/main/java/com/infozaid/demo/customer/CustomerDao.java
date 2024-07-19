package com.infozaid.demo.customer;



import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> getAllCustomers();
    Optional<Customer>  findCustomerById(Integer id);
    void insertCustomer(Customer customer);

    boolean existPersonWithEmail(String email);

    boolean existPersonWithId(Integer id);
    void delete(Integer id);
    void upddateCustomer(Customer customer);
}
