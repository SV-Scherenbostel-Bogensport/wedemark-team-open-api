package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.MatchInfoDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.TeamDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Match;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Set;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.MatchRepository;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.SetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MatchService {

    final MatchRepository matchRepository;
    final SetRepository setRepository;

    public MatchService(MatchRepository matchRepository, SetRepository setRepository) {
        this.matchRepository = matchRepository;
        this.setRepository = setRepository;
    }

    // Alle Matches Laden
    public List<Match> getAllMatches(){
        return matchRepository.findAll();
    }

    // Match nach ID finden
    public Match getMatchById(Integer id){
        return matchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match nicht gefunden mit ID: " + id));
    }

    // Match nach ID finden und mit Sets ausgeben
    public MatchInfoDto getMatchWithSets(Integer id) {

        Match match = matchRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match nicht gefunden mit ID: " + id));

        List<Set> sets = setRepository.findByMatchIdOrderBySetIndex(id);

        TeamDto team1 = match.getTeam1() != null
                ? new TeamDto(match.getTeam1().getTeamId(), match.getTeam1().getName())
                : null;

        TeamDto team2 = match.getTeam2() != null
                ? new TeamDto(match.getTeam2().getTeamId(), match.getTeam2().getName())
                : null;

        String target1Code = match.getTarget1() != null ? match.getTarget1().getCode() : null;
        String target2Code = match.getTarget2() != null ? match.getTarget2().getCode() : null;

        return new MatchInfoDto(
            match.getMatchId(),
            match.getDescription(),
            team1,
            team2,
            match.getWinnerTeamId(),
            target1Code,
            target2Code,
            match.getRound().getIsKnockOut(),
            match.getStatus(),
            sets
        );
    }
}
