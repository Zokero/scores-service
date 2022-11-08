package com.pkuk.scores.infrastructure;

import com.pkuk.scores.domain.match.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("SELECT m FROM Match m WHERE m.hostName = :teamName OR m.guestName = :teamName")
    Collection<Match> findAllByTeamName(@Param("teamName") String teamName);

    @Modifying(flushAutomatically = true)
    @Transactional
    @Query("UPDATE Match m SET m.hostScore = :hostScore, m.guestScore = :guestScore WHERE m.id = :id")
    void update(@Param("hostScore") String hostScore, @Param("guestScore") String guestScore, @Param("id") Long id);

    @Query("SELECT m FROM Match m WHERE round = :round")
    Collection<Match> getByRound(@Param("round") int round);

}

