package com.pkuk.scores.scrapper;

import com.pkuk.scores.match.dto.MatchBatchInsertDto;
import com.pkuk.scores.match.dto.RoundInsertDto;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
class ScrappedRound {
    private RoundInsertDto roundNumber;
    private Set<MatchBatchInsertDto> matchBatchInsertDtoList;
}
