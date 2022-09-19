package com.pkuk.scores.domain.scrapers;

import com.pkuk.scores.application.ScoreSystem;
import com.pkuk.scores.task.ScrapTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.pkuk.scores.infrastructure")
public class ScrapersConfiguration {

    @Bean
    public LzpnScraper lzpnScrapper() {
        return new LzpnScraper();
    }

    @Bean
    public Minut90Scraper minut90Scrapper() { return new Minut90Scraper(); }

    @Bean
    public ScrapTask scrapLzpnTask(ScoreSystem scoreSystem){
        return new ScrapTask(lzpnScrapper(), minut90Scrapper(), scoreSystem);
    }

}
