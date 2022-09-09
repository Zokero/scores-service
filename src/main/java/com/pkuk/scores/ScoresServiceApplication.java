package com.pkuk.scores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScoresServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScoresServiceApplication.class, args);
    }

}
