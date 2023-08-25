package com.pkuk.scores.match.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultDto {

    private int hostScore;
    private int guestScore;

}
