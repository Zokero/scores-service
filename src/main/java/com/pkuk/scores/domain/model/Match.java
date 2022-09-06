package com.pkuk.scores.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Match {

    private String hostName;
    private String guestName;
    private String hostGoals;
    private String guestGoals;
    private String address;
    private LocalDateTime matchDate;
    private int round;
}
