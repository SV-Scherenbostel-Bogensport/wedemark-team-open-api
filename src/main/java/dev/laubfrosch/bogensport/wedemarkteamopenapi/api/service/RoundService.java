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

    // Aktive Runde ermitteln
    public Round getCurrentNextOrLastRound() {

        Optional<Round> round;

        // Aktive Runde ermitteln
        round = roundRepository.getCurrentRound();
        if (round.isPresent()) {
            return round
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.GONE));
        }

        //Fallback, falls keine aktive Runde: Nächste anstehende Runde ermitteln
        round = roundRepository.getNextUpcomingRound();
        if (round.isPresent()) {
            return round
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.GONE));
        }

        //Fallback, falls keine anstehende Runde: Letzte beendete Runde ermitteln
        round = roundRepository.getLatestFinishedRoundWithoutActiveBefore();
        if (round.isPresent()) {
            return round
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.GONE));
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // Letzte Runde laden
    public Round getLastFinishedRound() {
        return roundRepository.getLatestFinishedRoundWithoutActiveBefore().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Keine beendete Runde gefunden"));
    }

    // Aktuelle Runde laden
    public Round getCurrentRound() {
        return roundRepository.getCurrentRound().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Keine aktuelle Runde gefunden"));
    }

    // Nächste Runde laden
    public Round getNextUpcomingRound() {
        return roundRepository.getNextUpcomingRound().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Keine anstehende Runde gefunden"));
    }

    // Aktuelle Runde auf 'laufend' setzen
    public void setUpcomingRoundOngoing() {
        Round round = this.getCurrentNextOrLastRound();
        matchRepository.updateMatchStatusByIds(3, round.getRoundId(), List.of(2, 4, 7)); //Todo: Über 'lable' lösen (3: 'laufen', 2: 'anstehend', 4: 'pausiert',  7: 'Einschießen')
    }
}
