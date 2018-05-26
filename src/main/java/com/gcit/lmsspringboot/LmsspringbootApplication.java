package com.gcit.lmsspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.gcit.lmsspringboot")
public class LmsspringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsspringbootApplication.class, args);
	}
}
