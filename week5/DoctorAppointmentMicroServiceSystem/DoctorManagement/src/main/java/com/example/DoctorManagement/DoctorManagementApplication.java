package com.example.DoctorManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DoctorManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoctorManagementApplication.class, args);
	}

}
