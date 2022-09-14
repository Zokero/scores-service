package com.pkuk.scores.domain.scrapers;

import com.pkuk.scores.task.ScrapTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScrapersConfiguration {

    @Bean
    public LzpnScraper lzpnScrapper() {
        return new LzpnScraper();
    }

    @Bean
    public Minut90Scraper minut90Scrapper() { return new Minut90Scraper(); }

    @Bean
    public ScrapTask scrapLzpnTask(){
        return new ScrapTask(lzpnScrapper(), minut90Scrapper());
    }

}
