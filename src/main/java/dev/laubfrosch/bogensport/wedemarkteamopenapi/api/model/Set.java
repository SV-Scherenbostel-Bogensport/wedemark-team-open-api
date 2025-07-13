package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "sets", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"match_id", "set_index"})
})
public class Set {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "set_id")
    private Integer setId;

    @Column(name = "match_id", nullable = false)
    @NotNull(message = "MatchId ist erforderlich")
    private Integer matchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", referencedColumnName = "match_id", nullable = false, insertable = false, updatable = false)
    @JsonIgnore
    private Match match;

    @Column(name = "set_index", nullable = false)
    @NotNull(message = "SetIndex ist erforderlich")
    private Short setIndex;

    @Column(name = "total_team1")
    private Integer totalTeam1;

    @Column(name = "total_team2")
    private Integer totalTeam2;

    @Column(name = "points_team1")
    private Integer pointsTeam1;

    @Column(name = "points_team2")
    private Integer pointsTeam2;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Set() {}

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}