package com.api.twstock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TwstockApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwstockApplication.class, args);
	}

}
