package com.pkuk.scores;

import com.pkuk.scores.domain.match.Match;
import com.pkuk.scores.infrastructure.MatchRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@ActiveProfiles(profiles = "test")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
class ScoresServiceApplicationTests {

    @Autowired
    MatchRepository repository;

    @Test
    void test_find_by_id() {
        //given
        Match match = new Match();

        //when
        repository.save(match);

        //then
        Optional<Match> match1 = repository.findById(match.getId());
        assertEquals(match, match1.get());
    }

    @Test
    void test_get_by_round_number() {
        //given
        Match match = new Match();

        //when
        repository.save(match);

        //then
        Collection<Match> match1 = repository.getByRound(match.getRound());
        assertEquals(List.of(match), match1);
    }

    @Test
    void test_find_all_by_team_name() {
        //given
        Match match = new Match();
        match.setHostName("dummyHost");
        Match match1 = new Match();
        match1.setHostName("dummyHost");

        //when
        repository.saveAll(List.of(match, match1));

        //then
        Collection<Match> allByTeamName = repository.findAllByTeamName(match.getHostName());
        assertEquals(List.of(match, match1), allByTeamName);
    }
}
