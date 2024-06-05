package com.getjobs.servicereg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceregApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceregApplication.class, args);
	}

}
