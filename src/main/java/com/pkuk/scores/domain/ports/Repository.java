package com.pkuk.scores.domain.ports;

import com.pkuk.scores.domain.match.Match;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.Optional;

public interface Repository {

    Collection<Match> getMatchesBy(int round);

    void add(Match match);

    Optional<Match> get(Long matchId);

    Collection<Match> getAllBy(String teamName);

    void updateMatch(Match match);

    Collection<Match> getAll(Sort sort);
}
