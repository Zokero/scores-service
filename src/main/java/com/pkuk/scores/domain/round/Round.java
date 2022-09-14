package com.pkuk.scores.domain.round;

import com.pkuk.scores.domain.match.Match;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

@Data
public class Round {

    private int number;
    private LocalDate startDate;
    private LocalDate endDate;
    private Collection<Match> matches;
}
