package com.pkuk.scores.scrapper;


import com.pkuk.scores.match.domain.MatchFacade;
import com.pkuk.scores.match.dto.MatchBatchInsertDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
public class ScrapScheduler {

    private final LzpnScraper lzpnScraper;
    private final MatchFacade matchFacade;

    @PostConstruct
    void initialize() {
        log.info("Initial Scrap All Rounds Task Started");
        List<ScrappedRound> scrappedRounds = lzpnScraper.scrapAllMatches(Urls.LZPN_URL);
        scrappedRounds.forEach(r -> {
            long roundId = matchFacade.saveRound(r.getRoundNumber());
            r.getMatchBatchInsertDtoList().forEach(m -> {
                long matchId = matchFacade.saveMatch(m, roundId);
                matchFacade.updateMatchWithResult(m.getResult(), matchId);
            });
        });
        log.info(scrappedRounds.toString());
    }

    @Scheduled(fixedDelay = 30000)
    public void getLzpnMatchInfo() {
        log.info("Scrap Single Round Task Started");
        Set<MatchBatchInsertDto> xxx = lzpnScraper.scrapSingleRound(1, Urls.LZPN_URL);
        log.info(xxx.toString());
    }

}
