package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.controller;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Score;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.service.ScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wedemarkteamopen/2025/scores")
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    // GET /api/scores - alle Scores abrufen
    @GetMapping
    public ResponseEntity<List<Score>> getAllScores() {
        List<Score> targets = scoreService.getAllScores();
        return ResponseEntity.ok(targets);
    }

    // GET /api/scores - alle Scores abrufen
    @GetMapping("/{scoreCode}")
    public ResponseEntity<Score> getScoreByScoreCode(@PathVariable String scoreCode) {
        return scoreService.getScoreByScoreCode(scoreCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
