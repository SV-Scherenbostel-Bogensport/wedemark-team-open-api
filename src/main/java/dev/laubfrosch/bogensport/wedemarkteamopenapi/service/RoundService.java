package dev.laubfrosch.bogensport.wedemarkteamopenapi.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Round;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.RoundRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RoundService {

    final RoundRepository roundRepository;

    public RoundService(RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
    }

    // Alle Runden laden
    public List<Round> getAllRounds() {
        return roundRepository.findAll();
    }

    // Runde nach ID finden
    public Round getRoundById(Integer id) {
        return roundRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Runde nicht gefunden mit ID: " + id));
    }

    // Alle Qualifikationsrunden laden
    public List<Round> getAllQualificationRounds() {
        return roundRepository.findByIsKnockOutFalse();
    }

    // Alle Finalrunden laden
    public List<Round> getAllKnockoutRounds() {
        return roundRepository.findByIsKnockOutTrue();
    }
}
