package com.pkuk.scores.task;

import com.pkuk.scores.domain.match.Match;
import com.pkuk.scores.domain.scrapers.LzpnScraper;
import com.pkuk.scores.domain.scrapers.Minut90Scraper;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Slf4j
public class ScrapTask {

    private final LzpnScraper lzpnScraper;
    private final Minut90Scraper minut90Scraper;

    public ScrapTask(LzpnScraper lzpnScraper, Minut90Scraper minut90Scraper) {
        this.lzpnScraper = lzpnScraper;
        this.minut90Scraper = minut90Scraper;
    }

    @Scheduled(fixedDelay = 15000)
    public void getLzpnMatchInfo() {
        log.info("............Running task................");
        ChromeDriver driver = initChromeDriver();
        List<Match> matchList = lzpnScraper.scrap(Urls.LZPN_URL, driver);
        log.info(matchList.toString());
        driver.quit();
    }

    @Scheduled(fixedDelay = 15000)
    public void get90MinutMatchInfo() {
        log.info("............Running task................");
        ChromeDriver driver = initChromeDriver();
        minut90Scraper.scrap(Urls.MINUT90_URL, driver);
        driver.quit();
    }

    private static ChromeDriver initChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        return new ChromeDriver(chromeOptions);
    }

}
