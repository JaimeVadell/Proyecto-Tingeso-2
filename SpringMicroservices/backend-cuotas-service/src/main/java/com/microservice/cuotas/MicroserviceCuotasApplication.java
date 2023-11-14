package com.microservice.cuotas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroserviceCuotasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceCuotasApplication.class, args);
	}

}
