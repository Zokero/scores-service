package com.pkuk.scores;

import lombok.ToString;

@ToString
public class WebsiteDto {
    private String firstClub;
    private String score;
    private String secondClub;

    public WebsiteDto(String firstClub, String score, String secondClub) {
        this.firstClub = firstClub;
        this.secondClub = secondClub;
        this.score = score;
    }
}
