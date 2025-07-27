package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.TeamNameResponse;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.*;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.MatchRepository;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.StatusRepository;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.TargetRepository;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class OverlayService {

    final MatchRepository matchRepository;
    final StatusRepository statusRepository;
    final TargetRepository targetRepository;
    final TeamRepository teamRepository;
    final RoundService roundService;

    public OverlayService(MatchRepository matchRepository, StatusRepository statusRepository, TargetRepository targetRepository, TeamRepository teamRepository, RoundService roundService) {
        this.matchRepository = matchRepository;
        this.statusRepository = statusRepository;
        this.targetRepository = targetRepository;
        this.teamRepository = teamRepository;
        this.roundService = roundService;
    }

    public TeamNameResponse getTeamNameByTargetCode(String targetCode) {

        Target target = targetRepository.findByCode(targetCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scheibe mit Code '" + targetCode + "' existiert nicht"));

        Match match = matchRepository.findActiveMatchByTargetId(target.getTargetId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Auf Scheibe '" + targetCode + "' läuft kein Match"));

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Auf Scheibe '" + target.getCode() + "' ist kein Team platziert");
        }

        return teamId;
    }

    @Transactional(readOnly = true)
    public Status getStatusByTargetCode(String targetCode) {

        Target target = targetRepository.findByCode(targetCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scheibe mit Code '" + targetCode + "' existiert nicht"));

        Round round = roundService.getActiveNextOrLastRound();

        return matchRepository.findStatusByRoundAndTarget(round, target)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Kein Match für Runde '" + round.getRoundId() + "' auf Scheibe '" + targetCode + "' gefunden"));
    }
}
