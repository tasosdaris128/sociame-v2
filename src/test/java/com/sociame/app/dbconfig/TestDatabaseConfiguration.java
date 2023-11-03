package com.sociame.app.dbconfig;

import com.sociame.app.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class TestDatabaseConfiguration {

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> container() {
        return new PostgreSQLContainer<>("postgres:13.10-alpine")
                .withUrlParam("TC_TMPFS", "/testtmpfs:rw")
                .withDatabaseName("sociame-test")
                .withUsername("postgres")
                .withPassword("postgres");
    }

    public static void main(String[] args) {
        SpringApplication.from(Application::main)
                .with(TestDatabaseConfiguration.class)
                .run(args);
    }

}
