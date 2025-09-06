// Sample integration test using Testcontainers with PostgreSQL.
// Copy into: src/test/java/com/carrefour/kata/api/ReservationPostgresIT.java
package com.carrefour.kata.api;

import com.carrefour.kata.DeliverySchedulerApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@SpringBootTest(classes = DeliverySchedulerApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers(disabledWithoutDocker = true)
class ReservationPostgresIT {

    @ServiceConnection
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

//    @DynamicPropertySource
//    static void configure(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.username", postgres::getUsername);
//        registry.add("spring.datasource.password", postgres::getPassword);
//        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate"); // or "update" temporarily
//    }

    @Test
    void contextLoads() {
        // Write your integration tests here (reserve a slot, etc.)
    }
}