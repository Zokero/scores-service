package com.pkuk.scores.match.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MatchBatchInsertDto {

    String hostName;
    String guestName;
    String address;
    LocalDateTime matchDate;
    ResultDto result;

}
