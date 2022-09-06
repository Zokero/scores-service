package com.pkuk.scores;

import com.pkuk.scores.domain.scrapper.LzpnScrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class ScoresServiceApplication implements CommandLineRunner {

	@Autowired
    LzpnScrapper lzpnScrapper;

	public static void main(String[] args) {
		SpringApplication.run(ScoresServiceApplication.class, args);

	}

	@Override
	public void run(String... args) {
		lzpnScrapper.scrapp();
	}
}
