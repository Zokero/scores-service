package com.pkuk.scores.selenium;

import com.pkuk.scores.WebsiteDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

import static com.pkuk.scores.selenium.Constants.MINUT90_URL;

@Slf4j
public class Scrapper {

    private WebDriver driver;
    private WebsiteDto websiteDto;
    private ChromeOptions options;

    public Scrapper() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver");
        options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    public List<WebsiteDto> scrapData() {
        driver.get(MINUT90_URL);

        String pageSource = driver.getPageSource();
        Document doc = Jsoup.parse(pageSource);

        List list = doc.getElementsByTag("b").eachText();

        Object firstPauseTeam = list.stream().filter(a -> a.toString().contains("Pauza")).findFirst().get();
        int pauseTeamIndex = list.indexOf(firstPauseTeam);

        // usuwam wszystkie indexy listy od poczatku do pierwszej wzmianki o pauzie, pozniej same wystapienia pauzy
        list.subList(0, pauseTeamIndex).clear();
        list.removeIf(value -> value.toString().contains("Pauza"));

        List<Object> matchList = new ArrayList<>(3);
        List<WebsiteDto> finalResult = new ArrayList<>();

        for(Object element : list) {
            matchList.add(element);

            if (matchList.size() == 3) {
                websiteDto = new WebsiteDto(
                        matchList.get(0).toString(),
                        matchList.get(1).toString(),
                        matchList.get(2).toString());

                finalResult.add(websiteDto);
                matchList.removeAll(matchList);
            }
        }

        log.info("final result: " + finalResult);
        return finalResult;
    }

    public void closeBrowser() {
        driver.close();
        driver.quit();
    }
}
