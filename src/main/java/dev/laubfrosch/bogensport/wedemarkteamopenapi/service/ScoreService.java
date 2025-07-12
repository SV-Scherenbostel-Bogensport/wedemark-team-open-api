package dev.laubfrosch.bogensport.wedemarkteamopenapi.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Score;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.ScoreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;

    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    // Alle Scores laden
    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }

    // Score nach Code finden
    public Score getScoreByScoreCode(String scoreCode) {
        return scoreRepository.findByScoreCode(scoreCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Score nicht gefunden mit Code: " + scoreCode));
    }
}
