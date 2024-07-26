package com.infozaid.demo.customer;

import com.infozaid.demo.AbstractTestContainers;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class CustomerJDBCDataAccessServiceTest extends AbstractTestContainers {

    private CustomerJDBCDataAccessService underTest;

    private CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        underTest=new CustomerJDBCDataAccessService(getJdbcTemplate(),customerRowMapper);
    }

    @Test
    void getAllCustomers() {
        Customer customer=new Customer(
                faker.name().fullName(),
                faker.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                20
        );
        List<Customer> customers=underTest.getAllCustomers();
        Assertions.assertThat(customers);
    }

    @Test
    void findCustomerById() {
    }

    @Test
    void insertCustomer() {
    }

    @Test
    void existPersonWithEmail() {
    }

    @Test
    void existPersonWithId() {
    }

    @Test
    void delete() {
    }

    @Test
    void upddateCustomer() {
    }
}