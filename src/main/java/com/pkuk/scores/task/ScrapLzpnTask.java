package com.pkuk.scores.task;

import com.pkuk.scores.domain.scrapper.LzpnScrapper;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class ScrapLzpnTask {

    private final LzpnScrapper lzpnScrapper;

    public ScrapLzpnTask(LzpnScrapper lzpnScrapper) {
        this.lzpnScrapper = lzpnScrapper;
    }

    @Scheduled(fixedDelay = 15000)
    public void scrap() {
        log.info("............Running task................");
        ChromeDriver driver = initChromeDriver();
        lzpnScrapper.scrap(Urls.LZPN_URL, driver);
        driver.quit();
    }

    private static ChromeDriver initChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        return new ChromeDriver(chromeOptions);
    }

}
