package com.pkuk.scores.match;

import com.pkuk.scores.match.domain.MatchFacade;
import com.pkuk.scores.match.dto.MatchDto;
import com.pkuk.scores.match.dto.MatchDtoRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
class MatchController {

    MatchFacade matchFacade;
    MatchDtoRepository matchDtoRepository;

    @PostMapping("matches")
    MatchDto newMatch(@RequestBody MatchDto matchDto){
        return matchDtoRepository.save(matchDto);
    }

    @GetMapping("matches")
    List<MatchDto> getMatches(){
        matchFacade.getAllMatches();
        return List.of(new MatchDto());
    }

}
