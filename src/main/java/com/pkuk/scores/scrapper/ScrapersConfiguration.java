package com.pkuk.scores.scrapper;

import com.pkuk.scores.match.domain.MatchFacade;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.pkuk.scores.infrastructure")
class ScrapersConfiguration {

    @Bean
    public static ChromeDriver chromeDriver() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--headless");
        return new ChromeDriver(chromeOptions);
    }

    @Bean
    public LzpnScraper lzpnScrapper(ChromeDriver chromeDriver) {
        return new LzpnScraper(chromeDriver);
    }

    //    public Minut90Scraper minut90Scrapper() { return new Minut90Scraper(); }
//
//    @Bean
//    public ProductionRepository productionRepository(MatchRepository repository){
//        return new ProductionRepository(repository);
//    }
    @Bean
    public ScrapScheduler scrapScheduler(LzpnScraper lzpnScraper, MatchFacade matchFacade) {
        return new ScrapScheduler(lzpnScraper, matchFacade);
    }

}
