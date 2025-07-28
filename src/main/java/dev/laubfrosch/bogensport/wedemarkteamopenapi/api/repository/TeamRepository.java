package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.*;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Round;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Target;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

    @Query("SELECT new dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.AttendeesDto(t.teamId, t.name, t.playerCount, t.lookingForTeammates, t.hasPayed) FROM Team t ORDER BY t.teamId")
    List<AttendeesDto> getAllTeams();

    @Query("SELECT count(t) FROM Team t")
    CountDto countTeams();

    @Query("SELECT new dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.GetTeamIdsDto(t.teamId, t.name) FROM Team t ORDER BY t.teamId")
    List<GetTeamIdsDto> getAllTeamIds();

    @Query("SELECT new dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.TeamNameResponse(CASE WHEN m.target1 = :target THEN m.team1.name ELSE m.team2.name END) FROM Match m WHERE m.round = :round AND (m.target1 = :target OR m.target2 = :target)")
    Optional<TeamNameResponse> findTeamByRoundAndTarget(Round round, Target target);
}