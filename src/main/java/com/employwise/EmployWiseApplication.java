package com.employwise;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@OpenAPIDefinition(info = @Info(title = "EmployWise: Employ Manager",version = "1.0",description = "Handle Employ Data and notify them using email sevice"))
public class EmployWiseApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmployWiseApplication.class, args);
	}
}
