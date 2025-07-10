package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
    Optional<Score> findByScoreCode(String scoreCode);
}
