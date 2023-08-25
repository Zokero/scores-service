package com.pkuk.scores.match.domain;

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
class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long matchId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "match_results",
            joinColumns =
                    {@JoinColumn(name = "match_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "result_id", referencedColumnName = "id")})
    private Result result;
    @Column(name = "host_name")
    private String hostName;
    @Column(name = "guest_name")
    private String guestName;
    @Column(name = "address")
    private String address;
    @Column(name = "match_date")
    private LocalDateTime matchDate;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "round_id", nullable = false)
    @ToString.Exclude
    private Round round;
    @Column(name = "local_update_date")
    private LocalDateTime lastUpdatedDate;
    @Column(name = "completed")
    boolean isCompleted;

    @PreUpdate
    @PrePersist
    private void beforeUpdate() {
        lastUpdatedDate = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Match match = (Match) o;
        return matchId != null && Objects.equals(matchId, match.matchId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
