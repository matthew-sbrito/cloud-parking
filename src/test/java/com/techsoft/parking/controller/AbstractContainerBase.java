package com.techsoft.parking.controller;

import org.testcontainers.containers.PostgreSQLContainer;

public abstract class AbstractContainerBase {

    static PostgreSQLContainer POSTGRES_SQL_CONTAINER = null;

    static {
        POSTGRES_SQL_CONTAINER = new PostgreSQLContainer("postgres:10-alpine");
        POSTGRES_SQL_CONTAINER.start();

        System.setProperty("spring.datasource.url", POSTGRES_SQL_CONTAINER.getJdbcUrl());
        System.setProperty("spring.datasource.username", POSTGRES_SQL_CONTAINER.getUsername());
        System.setProperty("spring.datasource.password", POSTGRES_SQL_CONTAINER.getPassword());
    }
}
