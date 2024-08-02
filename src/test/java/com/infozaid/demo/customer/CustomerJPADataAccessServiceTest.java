package com.infozaid.demo.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        autoCloseable= MockitoAnnotations.openMocks(this);
        underTest=new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllCustomers() {
        underTest.getAllCustomers();

        verify(customerRepository).findAll();
    }

    @Test
    void findCustomerById() {

        int id = 1;

        underTest.findCustomerById(id);

        verify(customerRepository).findById(id);
    }

    @Test
    void insertCustomer() {
        Customer customer=new Customer(1,"Ali","ali@test.com",20);

        underTest.insertCustomer(customer);

        verify(customerRepository).save(customer);
    }

    @Test
    void existPersonWithEmail() {
        String email="foo@gmail.com";

        underTest.existPersonWithEmail(email);

        verify(customerRepository).existsCustomerByEmail(email);
    }

    @Test
    void existPersonWithId() {
        int id = 1;
        underTest.existPersonWithId(id);

        verify(customerRepository).existsCustomerById(id);
    }

    @Test
    void delete() {
        int id = 1;

        underTest.delete(id);

        verify(customerRepository).deleteById(id);
    }

    @Test
    void updateCustomer() {
        Customer customer=new Customer(1,"Ali","ali@gmail.com",2);

        underTest.updateCustomer(customer);

        verify(customerRepository).save(customer);
    }
}