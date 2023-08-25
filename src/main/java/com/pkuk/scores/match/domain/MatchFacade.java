package com.pkuk.scores.match.domain;

import com.pkuk.scores.match.dto.MatchBatchInsertDto;
import com.pkuk.scores.match.dto.ResultDto;
import com.pkuk.scores.match.dto.RoundInsertDto;
import com.pkuk.scores.match.exception.MatchDoesNotExist;
import com.pkuk.scores.match.exception.RoundAlreadyExistException;
import com.pkuk.scores.match.exception.RoundDoesNotExist;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
public final class MatchFacade {

    private MatchRepository matchRepository;
    private ResultRepository resultRepository;
    private RoundRepository roundRepository;

    public Collection<Match> getAllMatches() {
        return matchRepository.findAll(Sort.by(Sort.Direction.ASC, "matchDate"));
    }

    public Collection<Match> getAllMatchesWithResults() {
        return matchRepository.findAll(Sort.by(Sort.Direction.ASC, "matchDate")).stream().filter(
                m -> m.getResult() != null
        ).collect(Collectors.toList());
    }

    public long saveRound(RoundInsertDto roundInsertDto) {
        Optional<Round> byRoundNumber = roundRepository.findByRoundNumber(roundInsertDto.getRoundNumber());
        if (byRoundNumber.isEmpty()) {
            Integer roundNumber = roundInsertDto.getRoundNumber();
            log.info("Saving new round with number " + roundNumber);
            Round savedRound = roundRepository.save(new Round(roundNumber));
            return savedRound.getRound_id();
        } else {
            Round round = byRoundNumber.get();
            throw new RoundAlreadyExistException("Round with id " + round.getRound_id() +
                    " and number " + round.getRoundNumber() + " already exists");
        }
    }

    public long saveMatch(MatchBatchInsertDto m, long roundId) {
        log.info("Saving match {}", m);
        Optional<Round> round = roundRepository.findById(roundId);
        if (round.isPresent()) {
            Match match = new Match();
            match.setHostName(m.getHostName());
            match.setGuestName(m.getGuestName());
            match.setAddress(m.getAddress());
            match.setMatchDate(m.getMatchDate());
            match.setRound(round.get());
            return matchRepository.save(match).getMatchId();
        }
        log.error("Cannot save match");
        throw new RoundDoesNotExist("Cannot find round with id " + roundId + " does not exists yet");
    }

    public void updateMatchWithResult(ResultDto resultDto, long matchId) {
        Optional<Match> match = matchRepository.findById(matchId);
        if (match.isPresent()) {
            Match match1 = match.get();
            Result result = new Result();
            result.setHostScore(resultDto.getHostScore());
            result.setGuestScore(resultDto.getGuestScore());
            result.setMatch(match1);
            match1.setResult(result);
            match1.setCompleted(checkIfMatchIsCompleted(resultDto));
            matchRepository.save(match1);
        } else {
            log.error("Cannot save result");
            throw new MatchDoesNotExist("Cannot find match with id " + matchId + " does not exists yet");
        }
    }

    private boolean checkIfMatchIsCompleted(ResultDto resultDto) {
        return resultDto.getGuestScore() != -1 && resultDto.getHostScore() != -1;
    }
}
