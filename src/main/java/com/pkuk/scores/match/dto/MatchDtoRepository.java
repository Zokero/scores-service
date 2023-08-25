package com.pkuk.scores.match.dto;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchDtoRepository extends CrudRepository<MatchDto, Long> {


}

