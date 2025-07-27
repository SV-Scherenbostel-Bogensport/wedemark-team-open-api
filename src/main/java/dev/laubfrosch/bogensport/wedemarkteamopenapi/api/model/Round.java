package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "rounds")
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "round_id")
    private Integer roundId;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_knock_out", nullable = false)
    private Boolean isKnockOut = false;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Round() {}

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}