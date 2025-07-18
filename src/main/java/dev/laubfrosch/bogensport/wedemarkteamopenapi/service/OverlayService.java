package dev.laubfrosch.bogensport.wedemarkteamopenapi.service;

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Target nicht gefunden mit Code: " + targetCode));

        Match match = matchRepository.findActiveMatchByTargetId(target.getTargetId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kein aktives Match auf Target " + targetCode + " gefunden"));

        Integer teamId;

        if (Objects.equals(match.getTarget1(), target)) {
            teamId = match.getTeam1Id();

        } else if (Objects.equals(match.getTarget2(), target)) {
            teamId = match.getTeam2Id();

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Auf Target" + targetCode + " ist kein Team platziert");
        }

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team nicht gefunden mit ID: " + teamId));

        return new TeamNameResponse(team.getName());
    }
}
