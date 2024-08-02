package com.infozaid.demo.customer;

import com.infozaid.demo.exception.DuplicateResourceException;
import com.infozaid.demo.exception.RequestValidationException;
import com.infozaid.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;;
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

    public void deleteCustomer(Integer id){
        if(!customerDao.existPersonWithId(id)){
           throw new ResourceNotFoundException("Customer with [%s] id not found:".formatted(id));
        }
        customerDao.delete(id);
    }

    public void updateCustomer(CustomerUpdateRequest customerUpdateRequest,Integer id){
        Customer customer=getCustomer(id);

        boolean changes=false;

        if(customerUpdateRequest.age()!=null && !customer.getAge().equals(customerUpdateRequest.age())){
            customer.setAge(customerUpdateRequest.age());
            changes=true;
        }
        if(customerUpdateRequest.name()!=null && !customer.getAge().equals(customerUpdateRequest.name())){
            customer.setName(customerUpdateRequest.name());
            changes = true;
        }
        if(customerUpdateRequest.email()!=null && !customer.getEmail().equals(customerUpdateRequest.email())){
            if(customerDao.existPersonWithEmail(customerUpdateRequest.email())){
                throw new DuplicateResourceException("email already taken");
            }
            customer.setEmail(customerUpdateRequest.email());
            changes = true;
        }

        if(!changes){
            throw new RequestValidationException("no data changes found");
        }

        customerDao.updateCustomer(customer);

    }
}
