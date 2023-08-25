package com.pkuk.scores.match.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ResultRepository extends JpaRepository<Result, Long> {
}
