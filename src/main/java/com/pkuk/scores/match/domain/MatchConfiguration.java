package com.pkuk.scores.match.domain;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MatchConfiguration {

    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    MatchFacade matchFacade(MatchRepository matchRepository, ResultRepository resultRepository, RoundRepository roundRepository){
        return new MatchFacade(matchRepository, resultRepository, roundRepository);
    }

    @Bean
    MatchFacade facade(){
        return matchFacade(InMemoryMatchRepositoryMock.newInstance(), );
    }
}
