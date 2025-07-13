package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.controller;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Match;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.service.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wedemarkteamopen/2025/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    // GET /api/matches - alle Matches abrufen
    @GetMapping
    public ResponseEntity<List<Match>> getAllMatches() {
        List<Match> matches = matchService.getAllMatches();
        return ResponseEntity.ok(matches);
    }

    // GET /api/matches/{id} - Match nach ID abrufen
    @GetMapping("/{id}")
    public ResponseEntity<Match> getMatchById(@PathVariable Integer id) {
        Match match = matchService.getMatchById(id);
        return ResponseEntity.ok(match);
    }

    // TODO: GET /api/matches/{id}/sets

    // TODO: PATCH /api/matches/{id}/teams // Teams setzen nach vorheriger Runde/Match
    // TODO: PATCH /api/matches/{id}/targets // nicht unbedingt nötig, da initiiert
    // TODO: PATCH /api/matches/{id}/totalPoints // Updaten nach jedem Satz
    // TODO: PATCH /api/matches/{id}/winner // Setzen nach match ende
    // oder
    // TODO: PATCH /api/matches/{id} // und optionalen Parametern (bisher favourite)

}
