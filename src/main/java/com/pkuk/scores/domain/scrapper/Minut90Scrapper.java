package com.pkuk.scores.domain.scrapper;

import com.pkuk.scores.domain.model.Match;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Minut90Scrapper implements Scrap {

    public void scrap(String scrapSourceUrl, WebDriver driver) {
        log.info(this.getClass().getSimpleName() + " started");
        driver.get(scrapSourceUrl);

        process(Jsoup.parse(driver.getPageSource()));
        log.info(this.getClass().getSimpleName() + " stopped");
    }

    private void process(Document rootDocument) {
        List list = rootDocument.getElementsByTag("b").eachText();

        List<Object> matchList = new ArrayList<>(3);

        for(Object element : filterList(list)) {
            matchList.add(element);

            if (matchList.size() == 3) {
                completeMatchInfo(matchList);
                matchList.removeAll(matchList);
            }
        }
    }

    private List filterList(List list) {
        Object firstPauseTeam = list.stream().filter(a -> a.toString().contains("Pauza")).findFirst().get();
        int pauseTeamIndex = list.indexOf(firstPauseTeam);

        // Remove all indexes list from beginning to first 'Pauza'
        list.subList(0, pauseTeamIndex).clear();
        list.removeIf(value -> value.toString().contains("Pauza"));
        list.removeIf(value -> value.toString().contains("Kolejka"));

        return list;
    }

    private void completeMatchInfo(List matchList) {
        String[] scores = matchList.get(1).toString().split("-");
        Match match = new Match();
        match.setHostName(matchList.get(0).toString());
        match.setGuestName(matchList.get(2).toString());
        match.setHostScore(scores[0]);
        match.setGuestScore(scores[1]);

        log.info(match.toString());
    }
}
