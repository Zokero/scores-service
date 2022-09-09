package com.pkuk.scores.configuration;

import com.pkuk.scores.domain.scrapper.LzpnScrapper;
import com.pkuk.scores.task.ScrapLzpnTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public LzpnScrapper lzpnScrapper() {
        return new LzpnScrapper();
    }

    @Bean
    public ScrapLzpnTask scrapLzpnTask(){
        return new ScrapLzpnTask(lzpnScrapper());
    }

}
