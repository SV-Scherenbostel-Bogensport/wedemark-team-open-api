package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.controller;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Round;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.service.RoundService;
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
    @GetMapping("/rounds/qualification")
    public ResponseEntity<List<Round>> getAllQualificationRounds() {
        List<Round> rounds = roundService.getAllQualificationRounds();
        return ResponseEntity.ok(rounds);
    }

    // GET /api/rounds/knockout - Alle Finalrunden abrufen
    @GetMapping("/rounds/knockout")
    public ResponseEntity<List<Round>> getAllKnockoutRounds() {
        List<Round> rounds = roundService.getAllKnockoutRounds();
        return ResponseEntity.ok(rounds);
    }

    // TODO: // GET /api/rounds/{id}/matches
}
