package com.linkplaza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class LinkPlazaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkPlazaApiApplication.class, args);
	}

}
