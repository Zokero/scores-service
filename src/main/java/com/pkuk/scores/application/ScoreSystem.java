package com.pkuk.scores.application;

import com.pkuk.scores.domain.match.Match;

import java.util.Collection;
import java.util.List;

public interface ScoreSystem {

    Collection<Match> getAllMatches();

    void saveAllMatches(List<Match> matches);




}
