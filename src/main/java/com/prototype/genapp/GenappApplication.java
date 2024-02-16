package com.prototype.genapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication

@OpenAPIDefinition(info = @Info(title = "GenApp", description = "GenApp Prototype", version = "v1"))

public class GenappApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenappApplication.class, args);
	}

}
