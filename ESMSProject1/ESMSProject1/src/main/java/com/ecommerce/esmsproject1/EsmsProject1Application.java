package com.ecommerce.esmsproject1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.ecommerce.esmsproject1.repository")
public class EsmsProject1Application {

	public static void main(String[] args) {

		SpringApplication.run(EsmsProject1Application.class, args);
	}

}
