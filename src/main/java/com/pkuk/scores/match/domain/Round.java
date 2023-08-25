package com.pkuk.scores.match.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Rounds")
@Setter
@Getter
@NoArgsConstructor
class Round {

    public Round(Integer roundNumber) {
        this.roundNumber = roundNumber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long round_id;
    @Column(name = "round_number")
    private Integer roundNumber;

}
