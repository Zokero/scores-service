package com.pkuk.scores.application;

import com.pkuk.scores.domain.ports.Repository;
import org.springframework.context.annotation.Configuration;

@Configuration
class ScoreSystemConfiguration {

    public static ScoreSystemImpl scoreSystem(Repository repository) {
        return new ScoreSystemImpl(repository);
    }

}
