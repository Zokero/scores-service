package com.pkuk.scores.infrastructure;

import com.pkuk.scores.domain.match.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("SELECT m FROM Match m WHERE m.hostName = :teamName OR m.guestName = :teamName")
    Collection<Match> findAllByTeamName(@Param("teamName") String teamName);

    @Modifying(flushAutomatically = true)
    @Transactional
    @Query("UPDATE Match m SET m.hostScore = ?1, m.guestScore = ?2 WHERE m.id = ?3")
    void update(String hostScore, String guestScore, Long id);

    @Query("SELECT m FROM Match m WHERE round = :round")
    List<Match> getByRound(@Param("round")int round);

}

