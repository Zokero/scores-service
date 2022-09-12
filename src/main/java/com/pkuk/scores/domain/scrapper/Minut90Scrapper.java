package com.pkuk.scores.domain.scrapper;

import com.pkuk.scores.domain.model.Match;
import com.pkuk.scores.domain.model.Score;
import com.pkuk.scores.domain.model.Teams;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Minut90Scrapper implements Scrap {

    private Score score = new Score();
    private Teams teams = new Teams();
    private Match match = new Match();

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
                String[] scores = matchList.get(1).toString().split("-");

                teams.setHostName(matchList.get(0).toString());
                teams.setGuestName(matchList.get(2).toString());
                score.setHostScore(scores[0]);
                score.setGuestScore(scores[1]);

                match.setTeams(teams);
                match.setScore(score);

                log.info(match.toString());
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
}
