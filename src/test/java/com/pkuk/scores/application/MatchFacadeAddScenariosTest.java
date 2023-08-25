package com.pkuk.scores.application;

import com.pkuk.scores.ScoreSystem;
import com.pkuk.scores.ScoreSystemConfiguration;
import com.pkuk.scores.match.domain.Match;
import com.pkuk.scores.infrastructure.InMemoryRepositoryMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MatchFacadeAddScenariosTest {


    @BeforeEach
    void beforeEach() {
        repositoryMock = InMemoryRepositoryMock.newInstance();
        scoreSystem = ScoreSystemConfiguration.scoreSystem(repositoryMock);
    }

    @ParameterizedTest
    @MethodSource("provideMatchesCollections")
    void save_all_matches(List<Match> input){
        //when
        scoreSystem.saveAllMatches(input);
        //then
        Collection<Match> allMatches = scoreSystem.getAllMatches();
        assertThat(allMatches).containsAll(input);
    }

    private static Stream<Arguments> provideMatchesCollections() {
        return Stream.of(
                Arguments.of(Collections.emptyList()),
                Arguments.of(List.of(new Match()))
        );
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
