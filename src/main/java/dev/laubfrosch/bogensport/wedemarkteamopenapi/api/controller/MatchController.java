package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.controller;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.MatchInfoDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Match;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // GET /api/matches/{id}/info - Erweiterte Info eines Matches abrufen
    @GetMapping("/{id}/info")
    public ResponseEntity<MatchInfoDto> getMatchWithSets(@PathVariable Integer id) {
        MatchInfoDto match = matchService.getMatchWithSets(id);
        return ResponseEntity.ok(match);
    }

    // POST /api/matches - neues Match erstellen
    @PostMapping
    public ResponseEntity<Match> createMatch(@RequestBody Match match) {
        Match createdMatch = matchService.createMatch(match);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMatch);
    }
    
    // PUT /api/matches/{id} - Match aktualisieren
    @PutMapping("/{id}")
    public ResponseEntity<Match> updateMatch(@PathVariable Integer id, @RequestBody Match matchDetails) {
        Match updatedMatch = matchService.updateMatch(id, matchDetails);
        return ResponseEntity.ok(updatedMatch);
    }

    // PATCH /api/matches/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<Match> partialUpdateMatch(@PathVariable Integer id, @RequestBody Match matchDetails) {
        Match updatedMatch = matchService.partialUpdateMatch(id, matchDetails);
        return ResponseEntity.ok(updatedMatch);
    }

    // DELETE /api/matches/{id} - Match löschen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Integer id) {
        matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }
}
