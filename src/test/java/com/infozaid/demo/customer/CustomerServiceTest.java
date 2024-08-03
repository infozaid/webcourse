package com.infozaid.demo.customer;

import com.infozaid.demo.exception.ResourceNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

// @ExtendWith(MockitoExtension.class) with this annotation we get rid of AutoClosable Boilerplate code
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;

    private CustomerService underTest;

    @BeforeEach
    void setUp() {
        underTest=new CustomerService(customerDao);
    }

    @Test
    void getAllCustomers() {
        underTest.getAllCustomers();

        verify(customerDao).getAllCustomers();
    }

    @Test
    void getCustomer() {
        int id = 10;

        Customer customer = new Customer(id,"Alex","alex@gmail.com",10);

        Mockito.when(customerDao.findCustomerById(id)).thenReturn(Optional.of(customer));

        Customer actual= underTest.getCustomer(id);

        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void WillThrowWhenCustomerReturnEmptyOptional() {
        int id = 10;

        Mockito.when(customerDao.findCustomerById(id)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(()->underTest.getCustomer(id))
                        .isInstanceOf(ResourceNotFoundException.class)
                                .hasMessage("Customer with id %s not found:".formatted(id));

    }

    @Test
    void addCustomer() {

        String email = "alex@gmail.com";

        Mockito.when(customerDao.existPersonWithEmail(email)).thenReturn(false);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest("Alex",email,20);

        underTest.addCustomer(request);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).insertCustomer(customerArgumentCaptor.capture());

        Customer customerCaptured = customerArgumentCaptor.getValue();

        assertThat(customerCaptured.getId()).isNull();

        assertThat(customerCaptured.getName()).isEqualTo(request.name());

        assertThat(customerCaptured.getEmail()).isEqualTo(request.email());

        assertThat(customerCaptured.getAge()).isEqualTo(request.age());


    }

    @Test
    void deleteCustomer() {
    }

    @Test
    void updateCustomer() {
    }
}