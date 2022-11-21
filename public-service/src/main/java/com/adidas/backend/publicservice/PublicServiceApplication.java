package com.adidas.backend.publicservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PublicServiceApplication {

	public static void main(String[] args) {
            SpringApplication.run(PublicServiceApplication.class, args);
	}

}
