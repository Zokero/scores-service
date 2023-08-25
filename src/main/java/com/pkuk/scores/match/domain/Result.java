package com.pkuk.scores.match.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name = "results")
class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "host_score")
    private Integer hostScore;
    @Column(name = "guest_score")
    private Integer guestScore;
    @OneToOne(mappedBy = "result")
    private Match match;

}
