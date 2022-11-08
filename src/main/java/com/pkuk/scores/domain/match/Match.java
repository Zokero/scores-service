package com.pkuk.scores.domain.match;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@Entity
@ToString
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hostName;
    private String guestName;
    private String hostScore;
    private String guestScore;
    private String address;
    private LocalDateTime matchDate;
    private int round;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Match match = (Match) o;
        return id != null && Objects.equals(id, match.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
