package com.microservice.estudiante;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroserviceEstudianteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceEstudianteApplication.class, args);
	}

}
