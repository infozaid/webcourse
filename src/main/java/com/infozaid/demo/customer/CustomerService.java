package com.infozaid.demo.customer;

import com.infozaid.demo.exception.DuplicateResourceException;
import com.infozaid.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers(){
        return customerDao.getAllCustomers();
    }

    public Customer getCustomer(Integer id){
        return customerDao.findCustomerById(id).orElseThrow(()-> new ResourceNotFoundException("Customer with id %s not found:".formatted(id)));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        //check if exist with same email
        if(customerDao.existPersonWithEmail(customerRegistrationRequest.email())){
            throw new DuplicateResourceException("email already taken");
        }
        Customer customer=new Customer(customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age());
        // add customer
        customerDao.insertCustomer(customer);
    }
}
