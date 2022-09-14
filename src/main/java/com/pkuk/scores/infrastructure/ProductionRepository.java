package com.pkuk.scores.infrastructure;

import com.pkuk.scores.domain.match.Match;
import com.pkuk.scores.domain.ports.Repository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
public class ProductionRepository implements Repository {

    private MatchRepository matchRepository;

    @Override
    public Collection<Match> getMatchesBy(int round) {
        return matchRepository.getByRound(round);
    }

    @Override
    public void add(Match match) {
        matchRepository.save(match);
    }

    @Override
    public Optional<Match> get(Long matchId) {
        return matchRepository.findById(matchId);
    }

    @Override
    public Collection<Match> getAllBy(String teamName) {
        return matchRepository.findAllByTeamName(teamName);
    }

    @Override
    public void updateMatch(Match match) {
        matchRepository.update(match.getHostScore(), match.getGuestScore(), match.getId());
    }

    @Override
    public Collection<Match> getAll(Sort sort) {
        return matchRepository.findAll(sort);
    }
}
