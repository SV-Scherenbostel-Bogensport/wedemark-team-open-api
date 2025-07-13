package dev.laubfrosch.bogensport.wedemarkteamopenapi.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.MatchInfoDto;
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
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match nicht gefunden mit ID: " + id));
        List<Set> sets = setRepository.findByMatchIdOrderBySetIndex(id);
        return new MatchInfoDto(match, sets);
    }
}
