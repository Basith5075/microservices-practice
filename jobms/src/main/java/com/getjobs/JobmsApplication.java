package com.getjobs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients // To work with OpenFeign
public class JobmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobmsApplication.class, args);
	}

}