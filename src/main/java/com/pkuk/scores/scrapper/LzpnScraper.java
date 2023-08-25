package com.pkuk.scores.scrapper;

import com.pkuk.scores.match.dto.MatchBatchInsertDto;
import com.pkuk.scores.match.dto.ResultDto;
import com.pkuk.scores.match.dto.RoundInsertDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
class LzpnScraper {

    public LzpnScraper(ChromeDriver driver) {
        this.driver = driver;
    }

    private final ChromeDriver driver;
    private Document rootDocument;

    private static final DateTimeFormatter roundDateFormatter = DateTimeFormatter.ofPattern("dd.M.yyyy");
    private static final DateTimeFormatter matchDateFormatter = DateTimeFormatter.ofPattern("dd.M.yyyy HH:mm");
    private static final Pattern localDatePattern = Pattern.compile("([0-9]{2}).([0-9]{2}).([0-9]{4})");
    private static final Pattern localDateTimePattern = Pattern.compile("([1-9]|([012][0-9])|(3[01])).(0?[1-9]|1[012]).\\d\\d\\d\\d [012]?[0-9]:[0-6][0-9]");

    public List<ScrappedRound> scrapAllMatches(String scrapSourceUrl) {
        log.info(this.getClass().getSimpleName() + " started");
        rootDocument = getRootDocument(scrapSourceUrl);
        List<ScrappedRound> roundBatchInsertDtoList = collectAllData();
        log.info(this.getClass().getSimpleName() + " stopped");
        return roundBatchInsertDtoList;
    }

    public Set<MatchBatchInsertDto> scrapSingleRound(int roundNumber, String scrapSourceUrl) {
        rootDocument = getRootDocument(scrapSourceUrl);
        String roundNumberStr = String.valueOf(roundNumber - 1);
        String xpath = String.format("//div[@class='results-screen__table league-results__item slider-item' and @data-slide='%s']", roundNumberStr);
        Element singleRound = rootDocument.selectXpath(xpath).get(0);
        return collectMatchesInfo(singleRound);
    }

    private Document getRootDocument(String scrapSourceUrl) {
        driver.navigate().refresh();
        driver.get(scrapSourceUrl);
        WebElement roundsRoot = new WebDriverWait(driver, Duration.ofSeconds(8))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@class='results-screen league-results__slider slider league-results--all']")));
        Document rootDocument = Jsoup.parse(roundsRoot.getAttribute("innerHTML"));
        Set<String> windowHandles = driver.getWindowHandles();
        if (windowHandles.size() > 1) {
            driver.close();
        }
        return rootDocument;
    }

    private List<ScrappedRound> collectAllData() {
        List<ScrappedRound> roundsList = new ArrayList<>();
        Elements roundElements = rootDocument.selectXpath("//div[@class='results-screen__table league-results__item slider-item']");
        for (Element round : roundElements) {
            Integer roundNumber = getRoundNumber(round);
            Set<MatchBatchInsertDto> matchBatchInsertDtoSet = collectMatchesInfo(round);
            roundsList.add(
                    ScrappedRound.builder()
                            .roundNumber(RoundInsertDto.builder().roundNumber(roundNumber).build())
                            .matchBatchInsertDtoList(matchBatchInsertDtoSet)
                            .build());
        }
        return roundsList;
    }

    private Set<MatchBatchInsertDto> collectMatchesInfo(Element round) {
        Elements matchElements = round.getElementsByClass("results-screen__table-row cf");
        Set<MatchBatchInsertDto> matchDtoAddScrappedDtos = new HashSet<>();
        for (Element game : matchElements) {
            List<String> gameInfo = game.getElementsByTag("span").stream()
                    .map(Element::text).toList();

            MatchBatchInsertDto matchBatchInsertDto = MatchBatchInsertDto.builder()
                    .hostName(gameInfo.get(0))
                    .guestName(gameInfo.get(2))
                    .address(getAddress(gameInfo))
                    .matchDate(getMatchDate(gameInfo.get(3)))
                    .result(getMatchResult(gameInfo))
                    .build();
            matchDtoAddScrappedDtos.add(matchBatchInsertDto);
        }
        return matchDtoAddScrappedDtos;
    }

    private static String getAddress(List<String> gameInfo) {
        if (gameInfo.size() == 5) {
            return gameInfo.get(4);
        }
        return "";
    }

    private ResultDto getMatchResult(List<String> gameInfo) {
        List<String> split = Arrays.stream(gameInfo.get(1).split(":"))
                .map(String::strip)
                .toList();

        if (!split.isEmpty()) {
            if (!split.get(0).isBlank()) {
                return ResultDto.builder()
                        .hostScore(Integer.parseInt(split.get(0)))
                        .guestScore(Integer.parseInt(split.get(1)))
                        .build();
            }
        }
        return ResultDto.builder().hostScore(-1).guestScore(-1).build();
    }

    private Integer getRoundNumber(Element round) {
        Element headerElement = round.firstElementChild();
        assert headerElement != null;
        String roundTitle = headerElement.getElementsByTag("h5").text();
        return Integer.parseInt(roundTitle.substring(roundTitle.indexOf(" ")).trim());
    }

    private LocalDateTime getMatchDate(String dateCandidate) {
        Matcher localDateTimeMatcher = localDateTimePattern.matcher(dateCandidate);
        Matcher localDateMatcher = localDatePattern.matcher(dateCandidate);
        if (localDateTimeMatcher.find()) {
            return LocalDateTime.parse(localDateTimeMatcher.group(), matchDateFormatter);
        } else if (localDateMatcher.find()) {
            return LocalDateTime.of(LocalDate.parse(localDateMatcher.group(), roundDateFormatter), LocalTime.MIDNIGHT);
        }
        return null;
    }
}

