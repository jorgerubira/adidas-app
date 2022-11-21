package com.adidas.front.admin_ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AdminUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminUiApplication.class, args);
	}

}
