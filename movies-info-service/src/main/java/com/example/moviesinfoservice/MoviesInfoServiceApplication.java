package com.example.moviesinfoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class MoviesInfoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviesInfoServiceApplication.class, args);
	}

}
