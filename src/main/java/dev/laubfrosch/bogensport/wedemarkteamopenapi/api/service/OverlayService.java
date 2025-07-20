package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.TeamNameResponse;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Match;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Target;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Team;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.MatchRepository;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.TargetRepository;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class OverlayService {

    final MatchRepository matchRepository;
    final TargetRepository targetRepository;
    final TeamRepository teamRepository;

    public OverlayService(MatchRepository matchRepository, TargetRepository targetRepository, TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.targetRepository = targetRepository;
        this.teamRepository = teamRepository;
    }

    public TeamNameResponse getTeamNameByTargetCode(String targetCode) {

        Target target = targetRepository.findByCode(targetCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scheibe mit Code '" + targetCode + "' existiert nicht"));

        Match match = matchRepository.findActiveMatchByTargetId(target.getTargetId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Auf Scheibe " + targetCode + " läuft kein Match"));

        Integer teamId = getTeamIdByMatchAndTarget(match, target);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mannschaft mit ID '" + teamId + "' nicht gefunden"));

        return new TeamNameResponse(team.getName());
    }

    private Integer getTeamIdByMatchAndTarget(Match match, Target target) {
        Integer teamId;

        if (Objects.equals(match.getTarget1(), target) && match.getTeam1Id() != null) {
            teamId = match.getTeam1Id();

        } else if (Objects.equals(match.getTarget2(), target) && match.getTeam2Id() != null) {
            teamId = match.getTeam2Id();

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Auf Scheibe " + target.getCode() + " ist kein Team platziert");
        }

        return teamId;
    }
}
