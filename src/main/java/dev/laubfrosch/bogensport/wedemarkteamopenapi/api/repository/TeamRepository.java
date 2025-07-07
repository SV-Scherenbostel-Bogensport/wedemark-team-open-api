package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.TeamDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

    @Query("SELECT new dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.TeamDto(t.teamId, t.name, t.lookingForTeammates, t.hasPayed) FROM Team t")
    List<TeamDto> getAllTeams();

    // Alle Teams die noch Mitspieler suchen
    // List<Team> findByLookingForTeammatesTrue();

    // Teams die bereits bezahlt haben
    // List<Team> findByHasPayedTrue();

    // Teams nach Namen suchen
    // List<Team> findByNameContainingIgnoreCase(String name);

    // Custom Query für Teams mit bestimmter Spieleranzahl
    // @Query("SELECT t FROM Team t WHERE t.playerCount = :count")
    // List<Team> findTeamsByPlayerCount(Integer count);
}