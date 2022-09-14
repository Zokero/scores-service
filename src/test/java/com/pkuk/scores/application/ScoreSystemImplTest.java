package com.pkuk.scores.application;

import com.pkuk.scores.domain.exception.RoundDoesNotExist;
import com.pkuk.scores.domain.match.Match;
import com.pkuk.scores.infrastructure.InMemoryRepositoryMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ScoreSystemImplTest {

    private ScoreSystemImpl scoreSystem;
    private InMemoryRepositoryMock repositoryMock;

    @BeforeEach
    void beforeEach() {
        repositoryMock = InMemoryRepositoryMock.newInstance();
        scoreSystem = ScoreSystemConfiguration.scoreSystem(repositoryMock);
    }

    @Test
    void get_all_matches() {
        //given
        Match e1 = new Match();
        scoreSystem.saveAllMatches(List.of(e1));

        //when
        Collection<Match> allMatches = scoreSystem.getAllMatches();

        //then
        assertThat(allMatches).containsExactly(e1);
    }

    @Test
    void report_error_when_round_does_not_exist() {
        //given
        Match match = new Match();
        int round = 11;
        match.setRound(round);

        //when
        Throwable throwable = catchThrowable(() -> scoreSystem.getAllMatchesFromSingleRound(round));

        //when
        then(throwable).isInstanceOf(RoundDoesNotExist.class).hasMessage("Round %s does not exist", round);
    }

    @Test
    void get_all_by_round_number() {
        //given
        Match match = new Match();
        match.setRound(1);

        scoreSystem.saveAllMatches(List.of(match));

        //when
        Collection<Match> allMatchesFromSingleRound = scoreSystem.getAllMatchesFromSingleRound(1);

        //then
        assertThat(allMatchesFromSingleRound).containsExactly(match);
    }


}