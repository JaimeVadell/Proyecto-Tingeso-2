package com.microservice.gestorAcademico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroserviceGestorAcademicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceGestorAcademicoApplication.class, args);
	}

}
