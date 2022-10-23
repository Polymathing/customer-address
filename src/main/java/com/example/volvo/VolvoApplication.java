package com.example.volvo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@EnableJpaAuditing
@SpringBootApplication
public class VolvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VolvoApplication.class, args);
	}

}