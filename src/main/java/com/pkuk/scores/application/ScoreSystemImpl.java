package com.pkuk.scores.application;

import com.pkuk.scores.domain.exception.RoundDoesNotExist;
import com.pkuk.scores.domain.match.Match;
import com.pkuk.scores.domain.ports.Repository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ScoreSystemImpl implements ScoreSystem {

    private Repository repository;

    public Collection<Match> getAllMatches() {
        return repository.getAll(Sort.by(Sort.Direction.ASC, "matchDate"));
    }

    @Override
    public Collection<Match> getAllMatchesFromSingleRound(int round) {
        Optional<Integer> roundsCount = Optional.of(round);
        if (roundsCount.get() < 10) {
            return repository.getMatchesBy(round);
        } else {
            throw new RoundDoesNotExist(String.format("Round %s does not exist", round));
        }
    }

    public void saveAllMatches(List<Match> matchList) {
        matchList.forEach(m -> repository.add(m));
    }

}
