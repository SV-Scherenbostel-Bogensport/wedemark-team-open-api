package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.controller;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.RoundMatchIdsResponse;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Match;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Round;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.service.MatchService;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.service.RoundService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wedemarkteamopen/2025/rounds")
public class RoundController {

    private final RoundService roundService;
    private final MatchService matchService;

    public RoundController(RoundService roundService, MatchService matchService) {
        this.roundService = roundService;
        this.matchService = matchService;
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

    // GET /api/rounds/{id}/active - Aktuelle oder abweichende Runde laden
    @GetMapping("/active")
    public ResponseEntity<Round> getCurrentRoundAlternative(
            @RequestParam(value = "direction", required = false) RoundDirection direction
    ) {
        Round round;

        if (direction == null) {
            round = roundService.getCurrentNextOrLastRound();
        } else {
            round = switch (direction) {
                case LAST -> roundService.getLastFinishedRound();
                case CURRENT -> roundService.getCurrentRound();
                case NEXT -> roundService.getNextUpcomingRound();
            };
        }

        if (round == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(round);
    }

    // PATCH /api/rounds/set-ongoing aktuell anstehende Matche auf 'laufend' setzen
    @PatchMapping("/set-ongoing")
    public ResponseEntity<String> setUpcomingRoundOngoing() {
        roundService.setUpcomingRoundOngoing();
        return ResponseEntity.ok("round set to ongoing successfully");
    }

    // GET /rounds/{id}/matches/by-target/{targetId}
    @GetMapping("/{id}/matches/by-target/{targetId}")
    public ResponseEntity<Match> getMatchByRoundAndTarget(@PathVariable Integer id, @PathVariable Integer targetId) {
        Match match = matchService.getMatchByRoundIdAndTargetId(id, targetId);
        return ResponseEntity.ok(match);
    }

    // Enum für Rundenwahl
    public enum RoundDirection {
        LAST,
        CURRENT,
        NEXT
    }
}
