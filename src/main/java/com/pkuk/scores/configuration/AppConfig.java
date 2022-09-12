package com.pkuk.scores.configuration;

import com.pkuk.scores.domain.scrapper.LzpnScrapper;
import com.pkuk.scores.domain.scrapper.Minut90Scrapper;
import com.pkuk.scores.task.ScrapTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public LzpnScrapper lzpnScrapper() {
        return new LzpnScrapper();
    }

    @Bean
    public Minut90Scrapper minut90Scrapper() { return new Minut90Scrapper(); }

    @Bean
    public ScrapTask scrapLzpnTask(){
        return new ScrapTask(lzpnScrapper(), minut90Scrapper());
    }

}
