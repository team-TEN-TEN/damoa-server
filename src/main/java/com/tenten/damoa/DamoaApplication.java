package com.tenten.damoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DamoaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DamoaApplication.class, args);
	}

}
