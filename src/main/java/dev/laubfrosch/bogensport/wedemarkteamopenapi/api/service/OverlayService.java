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

        Round round = roundService.getActiveNextOrLastRound();

        return teamRepository.findTeamByRoundAndTarget(round, target)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "In der '" + round.getDescription() + "' findet kein Match auf Scheibe '" + targetCode + "' statt"));
    }

    @Transactional(readOnly = true)
    public Status getStatusByTargetCode(String targetCode) {

        Target target = targetRepository.findByCode(targetCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scheibe mit Code '" + targetCode + "' existiert nicht"));

        Round round = roundService.getActiveNextOrLastRound();

        return statusRepository.findStatusByRoundAndTarget(round, target)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "In der '" + round.getDescription() + "' findet kein Match auf Scheibe '" + targetCode + "' statt"));
    }
}
