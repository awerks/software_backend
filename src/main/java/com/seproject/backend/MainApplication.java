package com.seproject.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	@GetMapping("/")
    public String hello() {
    	return String.format("Hello world from Java Spring Boot!");
    }
	@GetMapping("/example")
	public java.util.Map<String, String> example() {
		return java.util.Map.of("message", "Example output");
	}
}
