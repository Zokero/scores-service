package com.pkuk.scores.domain.scrapper;

import com.pkuk.scores.domain.model.Match;
import com.pkuk.scores.domain.model.Round;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LzpnScrapper {

    private final String HEADLESS = "--headless";
    private final String URL = "https://lzpn.org/terminarze-i-tabele/#48506";
    private final ChromeOptions chromeOptions = new ChromeOptions();
    private final DateTimeFormatter roundDateformatter = DateTimeFormatter.ofPattern("dd.M.yyyy");
    private final DateTimeFormatter matchDateformatter = DateTimeFormatter.ofPattern("dd.M.yyyy HH:mm");
    private final Pattern localDateFormatter = Pattern.compile("([0-9]{2}).([0-9]{2}).([0-9]{4})");
    private final Pattern localDateTimeFormatter = Pattern.compile("([1-9]|([012][0-9])|(3[01])).([0]{0,1}[1-9]|1[012]).\\d\\d\\d\\d [012]{0,1}[0-9]:[0-6][0-9]");

    public void scrapp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
        chromeOptions.addArguments(HEADLESS);
        WebDriver chromeDriver = new ChromeDriver(chromeOptions);
        chromeDriver.get(URL);
        WebElement roundsRoot = new WebDriverWait(chromeDriver, Duration.ofSeconds(8))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@class='results-screen league-results__slider slider league-results--all']")));
        Document rootDocument = Jsoup.parse(roundsRoot.getAttribute("innerHTML"));
        chromeDriver.close();
        chromeDriver.quit();

        process(rootDocument);
    }

    private void process(Document rootDocument) {
        Elements roundElements = rootDocument.selectXpath("//div[@class='results-screen__table league-results__item slider-item']");
        for (Element round : roundElements) {
            Element headerElement = round.firstElementChild();
            Elements matchElements = round.getElementsByClass("results-screen__table-row cf");
            Round roundObj = new Round();
            createRoundNumber(headerElement.getElementsByTag("h5").text(), roundObj);
            createRoundDate(headerElement.getElementsByTag("span").text(), roundObj);
            System.out.println(roundObj);
            processMatches(matchElements, roundObj.getNumber());
        }
    }

    private void processMatches(Elements matchElements, int roundNumber) {
        for (Element game : matchElements) {
            Match matchObj = new Match();
            matchObj.setRound(roundNumber);
            List<String> span = game.getElementsByTag("span").stream()
                    .map(Element::text).toList();
            createMatchInfo(matchObj, span);
            createMatchResult(matchObj, span);
            System.out.println(matchObj);
        }
    }

    private void createMatchInfo(Match matchObj, List<String> span) {
        matchObj.setHostName(span.get(0));
        matchObj.setGuestName(span.get(2));
        createMatchDate(span.get(3), matchObj);
        if (span.size() == 5) {
            matchObj.setAddress(span.get(4));
        } else {
            matchObj.setAddress("");
        }
    }

    private void createMatchResult(Match matchObj, List<String> resutl) {
        List<String> split = Arrays.stream(resutl.get(1).split(":"))
                .map(String::strip)
                .toList();
        if (!split.isEmpty()) {
            matchObj.setHostGoals(split.get(0));
            matchObj.setGuestGoals(split.get(1));
        } else {
            matchObj.setHostGoals("");
            matchObj.setGuestGoals("");
        }
    }

    private void createRoundNumber(String roundNumber, Round round) {
        round.setNumber(
                Integer.parseInt(roundNumber.substring(roundNumber.indexOf(" ")).trim()));
    }

    public void createRoundDate(String dateCandidate, Round round) {
        Matcher matcher = localDateFormatter.matcher(dateCandidate);
        List<LocalDate> dateList = new ArrayList<>();
        while (matcher.find()) {
            dateList.add(LocalDate.parse(matcher.group(), roundDateformatter));
        }
        round.setStartDate(dateList.get(0));
        round.setEndDate(dateList.get(1));
    }

    public void createMatchDate(String dateCandidate, Match match) {
        Matcher localDateTimeMatcher = localDateTimeFormatter.matcher(dateCandidate);
        Matcher localDateMatcher = localDateFormatter.matcher(dateCandidate);
        if(localDateTimeMatcher.find()){
            match.setMatchDate(LocalDateTime.parse(localDateTimeMatcher.group(), matchDateformatter));
        }else if(localDateMatcher.find()){
            match.setMatchDate(LocalDateTime.of(LocalDate.parse(localDateMatcher.group(), roundDateformatter), LocalTime.MIDNIGHT));
        }
    }
}
