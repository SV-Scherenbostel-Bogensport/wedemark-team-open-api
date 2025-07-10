package dev.laubfrosch.bogensport.wedemarkteamopenapi.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Score;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.ScoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    // Target nach Code finden
    public Optional<Score> getScoreByScoreCode(String scoreCode) {
        return scoreRepository.findByScoreCode(scoreCode);
    }
}
