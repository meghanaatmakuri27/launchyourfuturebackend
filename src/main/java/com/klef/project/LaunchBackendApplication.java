package com.klef.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin("*")
public class LaunchBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaunchBackendApplication.class, args);
		System.out.println("welcome to lyf backend");
	}

}
