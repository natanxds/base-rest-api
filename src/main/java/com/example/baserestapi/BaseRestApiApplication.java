package com.example.baserestapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Base REST API",
        version = "1.0",
        description = "Base REST API for Order Management System - Foundation for Microservices Architecture"
    )
)
public class BaseRestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseRestApiApplication.class, args);
    }
} 