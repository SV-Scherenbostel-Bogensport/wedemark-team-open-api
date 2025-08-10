package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "match_id")
    private Integer matchId;

    @Column(name = "round_id", nullable = false)
    private Integer roundId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id", referencedColumnName = "round_id", nullable = false, insertable = false, updatable = false)
    @JsonIgnore
    private Round round;

    @Column(name = "status_id", nullable = false)
    private Integer statusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "status_id", nullable = false, insertable = false, updatable = false)
    @JsonIgnore
    private Status status;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "team1_id")
    private Integer team1Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team1_id", referencedColumnName = "team_id", insertable = false, updatable = false)
    @JsonIgnore
    private Team team1;

    @Column(name = "team2_id")
    private Integer team2Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team2_id", referencedColumnName = "team_id", insertable = false, updatable = false)
    @JsonIgnore
    private Team team2;

    @Column(name = "target1_id")
    private Integer target1Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target1_id", referencedColumnName = "target_id", insertable = false, updatable = false)
    @JsonIgnore
    private Target target1;

    @Column(name = "target2_id")
    private Integer target2Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target2_id", referencedColumnName = "target_id", insertable = false, updatable = false)
    @JsonIgnore
    private Target target2;

    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    public Match() {}

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
