package com.example.springgumballworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringGumballWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringGumballWorkerApplication.class, args);
	}

}
