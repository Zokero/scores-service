package com.pkuk.scores.infrastructure;

import com.pkuk.scores.domain.match.Match;
import com.pkuk.scores.domain.ports.Repository;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryRepositoryMock implements Repository {

    private final Map<Long, Match> inMemoryDb = new HashMap<>();

    private InMemoryRepositoryMock() {
    }

    public static InMemoryRepositoryMock newInstance() {
        return new InMemoryRepositoryMock();
    }

    @Override
    public Collection<Match> getMatchesBy(int round) {
        return inMemoryDb.values().stream().filter(m -> m.getRound() == round).collect(Collectors.toList());
    }

    @Override
    public void add(Match match) {
        inMemoryDb.put(match.getId(), match);
    }

    @Override
    public Optional<Match> get(Long matchId) {
        return Optional.of(inMemoryDb.get(matchId));
    }

    @Override
    public Collection<Match> getAllBy(String teamName) {
        return null;
    }

    @Override
    public void updateMatch(Match match) {
        inMemoryDb.put(match.getId(), match);
    }

    @Override
    public Collection<Match> getAll(Sort sort) {
        return inMemoryDb.values().stream().toList();
    }

}
