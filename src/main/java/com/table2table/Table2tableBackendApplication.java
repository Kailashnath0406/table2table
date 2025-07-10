package com.table2table;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class Table2tableBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(Table2tableBackendApplication.class, args);
	}

}
