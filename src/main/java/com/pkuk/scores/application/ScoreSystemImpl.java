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

    public void saveAllMatches(List<Match> matchList) {
        matchList.forEach(m -> repository.add(m));
    }

}
