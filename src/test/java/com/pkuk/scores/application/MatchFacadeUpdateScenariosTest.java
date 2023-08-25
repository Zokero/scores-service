package com.pkuk.scores.application;

import com.pkuk.scores.ScoreSystem;
import com.pkuk.scores.ScoreSystemConfiguration;
import com.pkuk.scores.match.domain.Match;
import com.pkuk.scores.infrastructure.InMemoryRepositoryMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DataJpaTest
class MatchFacadeUpdateScenariosTest {

    private ScoreSystem scoreSystem;
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
        scoreSystem.updateMatch(e1);

        //when
        Collection<Match> allMatches = scoreSystem.getAllMatches();

        //then
        assertThat(allMatches).containsExactly(e1);
    }

}
