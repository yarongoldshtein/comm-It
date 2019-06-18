package com.server.commIt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class CommItApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommItApplication.class, args);
	}

}
