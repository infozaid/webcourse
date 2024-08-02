package com.infozaid.demo.customer;

import com.infozaid.demo.AbstractTestContainers;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

class CustomerJDBCDataAccessServiceTest extends AbstractTestContainers {

    private CustomerJDBCDataAccessService underTest;

    private CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessService(getJdbcTemplate(),
                customerRowMapper
        );
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

        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        Customer customers= new Customer(
                faker.name().fullName(),
                email,
                20
        );

        underTest.insertCustomer(customers);

        int id = underTest.getAllCustomers().stream().filter(c->c.getEmail().equals(email)).map(Customer::getId)
                .findFirst().orElseThrow();

        Optional<Customer> actual = underTest.findCustomerById(id);

        Assertions.assertThat(actual).isPresent().hasValueSatisfying(c->{
            Assertions.assertThat(c.getId()).isEqualTo(id);
            Assertions.assertThat(c.getName()).isEqualTo(customers.getName());
            Assertions.assertThat(c.getEmail()).isEqualTo(customers.getEmail());
            Assertions.assertThat(c.getAge()).isEqualTo(customers.getAge());
        });
    }

    @Test
    void willReturnEmptyWhenSelectCustomerById() {
        int id = 0;

        var actual =  underTest.findCustomerById(id);

        Assertions.assertThat(actual).isEmpty();
    }

    @Test
    void insertCustomer() {
        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        Customer customers= new Customer(
                faker.name().fullName(),
                email,
                30
        );

        underTest.insertCustomer(customers);
    }

    @Test
    void existPersonWithEmail() {
        String email=faker.internet().safeEmailAddress()+"-"+ UUID.randomUUID();
        String name=faker.name().fullName();

        Customer customer=new Customer(name,
                email,
                25);
        underTest.insertCustomer(customer);

        boolean actual=underTest.existPersonWithEmail(email);

        Assertions.assertThat(actual).isTrue();

    }

    @Test
    void existPersonWithEmailReturnFalseWhenDoesNotExist() {
        String email=faker.internet().safeEmailAddress()+"-"+UUID.randomUUID();
        boolean actual=underTest.existPersonWithEmail(email);
        Assertions.assertThat(actual).isFalse();
    }


    @Test
    void existPersonWithId() {
        String email=faker.internet().safeEmailAddress()+"-"+UUID.randomUUID();
        String name=faker.name().fullName();

        Customer customer =new Customer(name,email,23);

        underTest.insertCustomer(customer);

        int id = underTest.getAllCustomers().stream().filter(c->c.getEmail().equals(email)).map(Customer::getId).findFirst().orElseThrow();

        var actual=underTest.existPersonWithId(id);

        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void deleteCustomerById() {
        String email=faker.internet().safeEmailAddress()+"-"+UUID.randomUUID();

        Customer customer=new Customer(
                faker.name().fullName(),
                email,
                20);

        underTest.insertCustomer(customer);

        int id=underTest.getAllCustomers()
                .stream()
                .filter(c->c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        underTest.delete(id);

        Optional<Customer> actual=underTest.findCustomerById(id);

        Assertions.assertThat(actual).isNotPresent();
    }


    @Test
    void updateCustomerName() {

        String email=faker.internet().safeEmailAddress()+"-"+UUID.randomUUID();
        Customer customer=new Customer(
                faker.name().fullName(),
                email,
                33);
        underTest.insertCustomer(customer);
        int id=underTest.getAllCustomers()
                .stream()
                .filter(c->c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        var newName="foo";

        Customer update = new Customer();
        update.setId(id);
        update.setName(newName);

        underTest.updateCustomer(update);

        Optional<Customer> actual=underTest.findCustomerById(id);
        Assertions.assertThat(actual).isPresent().hasValueSatisfying(c->{
            Assertions.assertThat(c.getId()).isEqualTo(id);
            Assertions.assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            Assertions.assertThat(c.getName()).isEqualTo(newName);
            Assertions.assertThat(c.getAge()).isEqualTo(customer.getAge());
        });


    }

    @Test
    void updateAllPropertiesToCustomer() {

        String email=faker.internet().safeEmailAddress()+"-"+UUID.randomUUID();
        Customer customer=new Customer(
                faker.name().fullName(),
                email,
                33);
        underTest.insertCustomer(customer);
        int id=underTest.getAllCustomers()
                .stream()
                .filter(c->c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        var newName="foo";
        var updateEmail=UUID.randomUUID().toString();

        Customer update = new Customer();
        update.setId(id);
        update.setName(newName);
        update.setEmail(updateEmail);
        update.setAge(20);

        underTest.updateCustomer(update);

        Optional<Customer> actual=underTest.findCustomerById(id);
        Assertions.assertThat(actual).isPresent().hasValue(update);
    }
}