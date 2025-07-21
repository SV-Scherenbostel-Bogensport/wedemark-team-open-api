package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.AttendeesDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.CountDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.GetTeamIdsDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

    @Query("SELECT new dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.AttendeesDto(t.teamId, t.name, t.playerCount, t.lookingForTeammates, t.hasPayed) FROM Team t ORDER BY t.teamId")
    List<AttendeesDto> getAllTeams();

    @Query("SELECT count(t) FROM Team t")
    CountDto countTeams();

    @Query("SELECT new dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.GetTeamIdsDto(t.teamId, t.name) FROM Team t ORDER BY t.teamId")
    List<GetTeamIdsDto> getAllTeamIds();
}