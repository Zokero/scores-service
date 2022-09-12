package com.pkuk.scores.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Match {

    private Teams teams;
    private Score score;
    private String address;
    private LocalDateTime matchDate;
    private int round;
}
