package com.pkuk.scores.application;

import com.pkuk.scores.domain.match.Match;
import com.pkuk.scores.infrastructure.InMemoryRepositoryMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

}
