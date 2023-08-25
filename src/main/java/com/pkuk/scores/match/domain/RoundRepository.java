package com.pkuk.scores.match.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface RoundRepository extends JpaRepository<Round, Long> {

    Optional<Round> findByRoundNumber(int roundNumber);

}
