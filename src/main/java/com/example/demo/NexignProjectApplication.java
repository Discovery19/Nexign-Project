package com.example.demo;

import com.example.demo.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(ApplicationConfig.class)
public class NexignProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(NexignProjectApplication.class, args);
	}

}
