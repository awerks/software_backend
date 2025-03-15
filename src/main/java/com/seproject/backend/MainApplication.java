package com.seproject.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@SpringBootApplication
@RestController
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	@Operation(summary = "Hello world endpoint", description = "Returns a hello world message", tags = { "Example" })
	@GetMapping("/")
	public String hello() {
		return String.format("Hello world from Java Spring Boot!");
	}

}
