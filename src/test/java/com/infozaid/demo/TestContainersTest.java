package com.infozaid.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
public class TestContainersTest extends AbstractTestContainers {


    @Test
    void canStartPostgresContainersDB() {
        Assertions.assertThat(postgreSqlContainer.isRunning()).isTrue();
        Assertions.assertThat(postgreSqlContainer.isCreated()).isTrue();
    //    Assertions.assertThat(postgreSqlContainer.isHealthy()).isTrue();
    }



}
