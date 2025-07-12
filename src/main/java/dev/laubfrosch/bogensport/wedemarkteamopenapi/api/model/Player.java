package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "players", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"team_id", "squad_number"})
})
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Integer playerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName = "team_id")
    @JsonIgnore
    private Team team;

    @JsonProperty("teamId")
    public Integer getTeamId() {
        return team != null ? team.getTeamId() : null;
    }

    @Column(name = "squad_number")
    private Short squadNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public Player() {}
}