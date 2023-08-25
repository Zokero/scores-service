package com.pkuk.scores.match.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "matches")
@Setter
@Getter
public class MatchDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "host_name")
    private String hostName;
    @Column(name = "guest_name")
    private String guestName;

}

