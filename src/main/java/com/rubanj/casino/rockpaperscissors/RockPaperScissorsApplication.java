package com.rubanj.casino.rockpaperscissors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RockPaperScissorsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RockPaperScissorsApplication.class, args);
	}

}
