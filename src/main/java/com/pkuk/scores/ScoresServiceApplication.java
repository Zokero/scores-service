package com.pkuk.scores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories({"com.pkuk.scores.match.domain", "com.pkuk.scores.match.dto"})
public class ScoresServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScoresServiceApplication.class, args);
	}

}
