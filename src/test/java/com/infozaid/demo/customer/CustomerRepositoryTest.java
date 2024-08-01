package com.infozaid.demo.customer;

import com.infozaid.demo.AbstractTestContainers;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestContainers {

    @Autowired
    private CustomerRepository underTest;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        System.out.println(applicationContext.getBeanDefinitionCount());
    }

    @Test
    void existsCustomerByEmail() {

        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customers= new Customer(
                faker.name().fullName(),
                email,
                20
        );

        underTest.save(customers);


        var actual = underTest.existsCustomerByEmail(email);

        Assertions.assertThat((actual)).isTrue();
    }

    @Test
    void existsCustomerByEmailWhenEmailIsNotPresent() {

        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        var actual = underTest.existsCustomerByEmail(email);

        Assertions.assertThat((actual)).isFalse();
    }

    @Test
    void existsCustomerById() {

        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customers= new Customer(
                faker.name().fullName(),
                email,
                20
        );

        underTest.save(customers);

        int id = underTest.findAll().stream().filter(c->c.getEmail().equals(email)).map(Customer::getId)
                .findFirst().orElseThrow();

        var actual = underTest.existsCustomerById(id);

        Assertions.assertThat((actual)).isTrue();
    }

    @Test
    void existsCustomerByIdWhenIdIsNotPresent() {
        int id = -1;
        var actual = underTest.existsCustomerById(id);
        Assertions.assertThat((actual)).isFalse();
    }
}