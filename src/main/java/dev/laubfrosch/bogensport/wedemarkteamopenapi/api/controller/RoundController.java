package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.controller;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.RoundMatchIdsResponse;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Round;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.service.RoundService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wedemarkteamopen/2025/rounds")
public class RoundController {

    private final RoundService roundService;

    public RoundController(RoundService roundService) {
        this.roundService = roundService;
    }

    // GET /api/rounds - alle Runden abrufen
    @GetMapping
    public ResponseEntity<List<Round>> getAllRounds() {
        List<Round> rounds = roundService.getAllRounds();
        return ResponseEntity.ok(rounds);
    }

    // GET /api/rounds/{id} - Runde nach ID abrufen
    @GetMapping("/{id}")
    public ResponseEntity<Round> getRoundById(@PathVariable Integer id) {
        Round round = roundService.getRoundById(id);
        return ResponseEntity.ok(round);
    }

    // GET /api/rounds/qualification - Alle Qualifikationsrunden abrufen
    @GetMapping("/qualification")
    public ResponseEntity<List<Round>> getAllQualificationRounds() {
        List<Round> rounds = roundService.getAllQualificationRounds();
        return ResponseEntity.ok(rounds);
    }

    // GET /api/rounds/knockout - Alle Finalrunden abrufen
    @GetMapping("/knockout")
    public ResponseEntity<List<Round>> getAllKnockoutRounds() {
        List<Round> rounds = roundService.getAllKnockoutRounds();
        return ResponseEntity.ok(rounds);
    }

    // GET /api/rounds/{id}/matches - Alle MatchIds von Matches der jeweiligen Runde abrufen
    @GetMapping("/{id}/matches")
    public ResponseEntity<RoundMatchIdsResponse> getAllRoundMatchIds(@PathVariable Integer id) {
        RoundMatchIdsResponse matches = roundService.getAllRoundMatchIds(id);
        return ResponseEntity.ok(matches);
    }
}
