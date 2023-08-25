package com.pkuk.scores.match.domain;

import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class InMemoryMatchRepositoryMock {

    private final Map<Long, Match> inMemoryDb = new HashMap<>();

    private InMemoryMatchRepositoryMock() {
    }

    public static InMemoryMatchRepositoryMock newInstance() {
        return new InMemoryMatchRepositoryMock();
    }

//    public Collection<Match> getMatchesBy(int round) {
//        return inMemoryDb.values().stream().filter(m -> m.getRound() == round).collect(Collectors.toList());
//    }

    public Match save(Match match) {
        Objects.requireNonNull(match);
        inMemoryDb.put(match.getMatchId(), match);
        return match;
    }

//    public void saveAllMatches(Collection<Match> matches) {
//        matches.forEach(m-> inMemoryDb.put(m.getId(), m));
//    }

    public Optional<Match> get(Long matchId) {
        return Optional.of(inMemoryDb.get(matchId));
    }

    public Collection<Match> getAllBy(String teamName) {
        return null;
    }

//    public void updateMatch(Match match) {
//        inMemoryDb.put(match.getId(), match);
//    }

    public Collection<Match> getAll(Sort sort) {
        return inMemoryDb.values().stream().toList();
    }

}
