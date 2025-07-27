package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.RoundMatchIdsResponse;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Round;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.MatchRepository;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.RoundRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RoundService {

    final RoundRepository roundRepository;
    final MatchRepository matchRepository;

    public RoundService(RoundRepository roundRepository, MatchRepository matchRepository) {
        this.roundRepository = roundRepository;
        this.matchRepository = matchRepository;
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

    // Alle Match Ids einer Runde laden
    public RoundMatchIdsResponse getAllRoundMatchIds(Integer id) {

        if (!roundRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Runde nicht gefunden mit ID: " + id);
        }

        List<Integer> roundMatchIds = matchRepository.findMatchIdsByRoundId(id);
        return new RoundMatchIdsResponse(id, roundMatchIds);
    }

    // Aktuelle Runde ermitteln
    public Round getActiveNextOrLastRound() {

        Optional<Integer> roundId;

        // Aktive Runde ermitteln
        roundId = roundRepository.findActiveRound();
        if (roundId.isPresent()) {
            return roundRepository.findById(roundId.get())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.GONE));
        }

        //Fallback, falls keine aktive Runde: Nächste anstehende Runde ermitteln
        roundId = roundRepository.findNextActiveRound();
        if (roundId.isPresent()) {
            return roundRepository.findById(roundId.get())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.GONE));
        }

        //Fallback, falls keine anstehende Runde: Letzte beendete Runde ermitteln
        roundId = roundRepository.findLastActiveRound();
        if (roundId.isPresent()) {
            return roundRepository.findById(roundId.get())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.GONE));
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
