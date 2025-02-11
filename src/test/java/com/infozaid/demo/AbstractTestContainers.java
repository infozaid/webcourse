package com.infozaid.demo;

import com.github.javafaker.Faker;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;


@Testcontainers
public abstract class AbstractTestContainers {


    @BeforeAll
    static void beforeAll() {
        Flyway flyway=Flyway.configure().dataSource(
                postgreSqlContainer.getJdbcUrl(),
                postgreSqlContainer.getUsername(),
                postgreSqlContainer.getPassword()
        ).load();
        flyway.migrate();
        System.out.println();
    }

    @Container
    protected static final PostgreSQLContainer<?> postgreSqlContainer=
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("infozaid-dao-unit-test")
                    .withUsername("postgres")
                    .withPassword("postgres");

    @DynamicPropertySource
    private static void registerDataSourceProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",
                postgreSqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username",
                postgreSqlContainer::getUsername);
        registry.add("spring.datasource.password",
                postgreSqlContainer::getPassword);

    }

    private static DataSource getDataSource(){
       return DataSourceBuilder.create().driverClassName(postgreSqlContainer.getDriverClassName())
                .url(postgreSqlContainer.getJdbcUrl())
                .username(postgreSqlContainer.getUsername())
                .password(postgreSqlContainer.getPassword()).build();
    }

    protected static JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(getDataSource());
    }

    protected static final Faker faker = new Faker();

}
