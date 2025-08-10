package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "team_Id")
    private Integer teamId;

    @Column(nullable = false)
    private String name;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "player_count")
    private Integer playerCount;

    @Column(name = "has_payed", nullable = false)
    private Boolean hasPayed = false;

    @Column(name = "looking_for_teammates", nullable = false)
    private Boolean lookingForTeammates = false;

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    public Team() {}

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}