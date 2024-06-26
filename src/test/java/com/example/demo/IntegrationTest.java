//package com.example.demo;
//
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//@Testcontainers
//public abstract class IntegrationTest {
//
//    public static PostgreSQLContainer<?> POSTGRES;
//
//    static {
//        POSTGRES = new PostgreSQLContainer<>("postgres:16")
//                .withDatabaseName("nexign")
//                .withUsername("postgres")
//                .withPassword("postgres");
//        POSTGRES.start();
//    }
//
//    @DynamicPropertySource
//    static void jdbcProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
//        registry.add("spring.datasource.username", POSTGRES::getUsername);
//        registry.add("spring.datasource.password", POSTGRES::getPassword);
//    }
//
//}
