package com.pkuk.scores.task;

import com.pkuk.scores.domain.scrapper.LzpnScrapper;
import com.pkuk.scores.domain.scrapper.Minut90Scrapper;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class ScrapTask {

    private final LzpnScrapper lzpnScrapper;
    private final Minut90Scrapper minut90Scrapper;

    public ScrapTask(LzpnScrapper lzpnScrapper, Minut90Scrapper minut90Scrapper) {
        this.lzpnScrapper = lzpnScrapper;
        this.minut90Scrapper = minut90Scrapper;
    }

    @Scheduled(fixedDelay = 15000)
    public void scrapLzpn() {
        log.info("............Running task................");
        ChromeDriver driver = initChromeDriver();
        lzpnScrapper.scrap(Urls.LZPN_URL, driver);
        driver.quit();
    }

    @Scheduled(fixedDelay = 15000)
    public void scrap90Minut() {
        log.info("............Running task................");
        ChromeDriver driver = initChromeDriver();
        minut90Scrapper.scrap(Urls.MINUT90_URL, driver);
        driver.quit();
    }

    private static ChromeDriver initChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        return new ChromeDriver(chromeOptions);
    }

}
