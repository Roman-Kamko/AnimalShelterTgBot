package com.team2.animalshelter.integration;

import com.team2.animalshelter.integration.annotation.IntegrationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

@IntegrationTest
@Sql({"classpath:scripts/data.sql"})
public abstract class IntegrationTestBase {

    private static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:15")
                    .withUsername("postgres")
                    .withPassword("postgres");

    @BeforeAll
    static void runContainer() {
        container.start();
    }

    @DynamicPropertySource
    static void postgresProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
    }

}
