package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model;

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
    private Integer teamId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "contact_email", length = 255)
    private String contactEmail;

    @Column(name = "player_count")
    private Integer playerCount;

    @Column(name = "has_payed", nullable = false)
    private Boolean hasPayed = false;

    @Column(name = "looking_for_teammates", nullable = false)
    private Boolean lookingForTeammates = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Team() {}

    public Team(String name, String contactEmail, Integer playerCount) {
        this.name = name;
        this.contactEmail = contactEmail;
        this.playerCount = playerCount;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}