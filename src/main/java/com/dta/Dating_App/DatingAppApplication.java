package com.dta.Dating_App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DatingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatingAppApplication.class, args);
	}

}