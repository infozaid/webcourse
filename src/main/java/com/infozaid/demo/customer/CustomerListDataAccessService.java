package com.infozaid.demo.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository("list")
public class CustomerListDataAccessService implements CustomerDao{

    public static List<Customer> customers;

    static {
        customers=new ArrayList<>();
        Customer alex = new Customer(
                1,
                "Alex",
                "alex@gmail.com",
                24
        );
        customers.add(alex);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    public Optional<Customer>  findCustomerById(Integer customerId) {
        return  customers.stream()
                .filter(c -> c.getId().equals(customerId))
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existPersonWithEmail(String email) {
        return customers.stream().anyMatch(c->c.getEmail().equals(email));
    }
}
