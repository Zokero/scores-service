package com.pkuk.scores.domain.scrapper;

import org.openqa.selenium.WebDriver;

public interface Scrap {
    void scrap(String scrapSourceUrl, WebDriver driver);
}
