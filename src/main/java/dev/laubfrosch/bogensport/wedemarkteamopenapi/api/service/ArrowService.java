package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.CreateArrowDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Arrow;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Match;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.ArrowRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ArrowService {

    final ArrowRepository   arrowRepository;
    private final MatchService matchService;

    public ArrowService(ArrowRepository arrowRepository, MatchService matchService) {
        this.arrowRepository = arrowRepository;
        this.matchService = matchService;
    }

    // Alle Pfeile laden
    public List<Arrow> getAllArrows() {
        return arrowRepository.findAll();
    }

    // Pfeil nach ID finden
    public Arrow getArrowById(Integer id) {
        return arrowRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pfeil nicht gefunden mit ID: " + id));
    }

    // Ein Pfeil über Schießzettel erstellen
    public Arrow upsertArrow(CreateArrowDto arrow) {

        Match match = matchService.getMatchByRoundIdAndTargetCode(arrow.roundId(), arrow.targetCode());
        Integer arrowId;
         scoreCode;
        Placerid
                setId
                arrowINdex

        String targetCode, Integer roundId, Integer squadNumber, Integer setIndex, Integer arrowIndex
                ;
        return upsertArrow(arrow.arrowIndex())
    }

    // Ein Pfeil erstellen oder bestehendes bearbeiten
    public Arrow upsertArrow(Arrow arrow) {
        Optional<Arrow> existingArrow = arrowRepository.findById(
                arrow.getArrowId()
        );
        if (existingArrow.isPresent()) {
            Arrow updateArrow = existingArrow.get();
            updateArrow.setScoreCode(arrow.getScoreCode());
            updateArrow.setPlayerId(arrow.getPlayerId());
            updateArrow.setSetId(arrow.getSetId());
            updateArrow.setArrowIndex(arrow.getArrowIndex());
            return arrowRepository.save(updateArrow);
        } else {
            arrow.setSetId(null);
            return arrowRepository.save(arrow);
        }
    }
}
