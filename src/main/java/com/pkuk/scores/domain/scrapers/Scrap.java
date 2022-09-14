package com.pkuk.scores.domain.scrapers;

import com.pkuk.scores.domain.match.Match;
import org.openqa.selenium.WebDriver;

import java.util.List;

public interface Scrap {
    List<Match> scrap(String scrapSourceUrl, WebDriver driver);
}
