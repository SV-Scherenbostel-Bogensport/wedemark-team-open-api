package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.JoinFormula;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "round_overview")
@Immutable
public class RoundOverview {

    @Id
    @Column(name = "round_id", nullable = false)
    private Integer roundId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinFormula("round_id")
    @JsonIgnore
    private Round round;

    @Column(name = "is_knock_out")
    private Boolean isKnockOut;

    @Column(name = "total_matches")
    private Integer totalMatches;

    @Column(name = "upcoming_matches")
    private Integer upcomingMatches;

    @Column(name = "active_matches")
    private Integer activeMatches;

    @Column(name = "finished_matches")
    private Integer finishedMatches;

    @Column(name = "round_status_id", nullable = false)
    private Integer statusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinFormula("round_status_id")
    @JsonIgnore
    private Status status;

    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}
