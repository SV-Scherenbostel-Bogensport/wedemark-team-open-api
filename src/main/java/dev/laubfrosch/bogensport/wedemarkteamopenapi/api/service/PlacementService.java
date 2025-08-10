package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.*;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.enumeration.PlacementJustification;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Round;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.RoundRepository;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.util.SqlFileReader;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlacementService {

    private final EntityManager entityManager;
    private final RoundRepository roundRepository;

    public PlacementService(EntityManager entityManager, RoundRepository roundRepository) {
        this.entityManager = entityManager;
        this.roundRepository = roundRepository;
    }

    public QualificationPlacementResponse getQualificationPlacement(){

        // TODO: Letzte komplett beendete Runde holen (momentan letzte Runde mit min. 1 ended)
        Optional<Round> round = roundRepository.getLastActiveQualificationRound();

        if (round.isEmpty()) {
            // TODO: Startliste zurückgeben
            throw new NotImplementedException();
        }

        String sql;

        try {
            //TODO: Nur bis "RundenId" auswerten
            sql = SqlFileReader.getSqlQueryFromFile("src/main/resources/static/sql/api/getQualificationPlacement.sql");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        List<QualificationPlacementDto> placements = new ArrayList<>();

        for (Object[] result : results) {

            TeamDto team = new TeamDto(
                    ((Number) result[0]).intValue(),
                    result[1].toString()
            );

            PointsDto totalMatchPoints = new PointsDto(
                    ((Number) result[2]).intValue(), // Long -> Integer
                    ((Number) result[3]).intValue()  // Long -> Integer
            );

            PointsDto totalSetPoints = new PointsDto(
                    ((Number) result[4]).intValue(), // Long -> Integer
                    ((Number) result[5]).intValue()  // Long -> Integer
            );

            float averageSetScore = ((Number) result[8]).floatValue();

            QualificationPlacementDto placement = new QualificationPlacementDto(
                    null,
                    PlacementJustification.UNKNOWN,
                    team,
                    totalMatchPoints,
                    totalSetPoints,
                    averageSetScore
            );

            placements.add(placement);
        }
        
        assignPlacementsAndJustifications(placements);
        return new QualificationPlacementResponse(round.get(), placements);
    }

    private void assignPlacementsAndJustifications(List<QualificationPlacementDto> placements) {
        int currentPlace = 1;

        for (int i = 0; i < placements.size(); i++) {
            QualificationPlacementDto current = placements.get(i);
            PlacementJustification justification = PlacementJustification.UNKNOWN;

            // Prüfe, ob es Gleichstände gibt
            if (i > 0) {
                QualificationPlacementDto previous = placements.get(i - 1);

                // Gleiche Matchpunkte?
                if (current.totalMatchPoints().won().equals(previous.totalMatchPoints().won())) {

                    // Gleiche Satzdifferenz?
                    int currentSetDiff = current.totalSetPoints().won() - current.totalSetPoints().lost();
                    int previousSetDiff = previous.totalSetPoints().won() - previous.totalSetPoints().lost();

                    if (currentSetDiff == previousSetDiff) {
                        currentPlace = previous.place();
                    }
                } else {
                    currentPlace = i + 1;
                }
            }

            QualificationPlacementDto updatedPlacement = new QualificationPlacementDto(
                    currentPlace,
                    justification,
                    current.team(),
                    current.totalMatchPoints(),
                    current.totalSetPoints(),
                    current.averageSetScore()
            );

            placements.set(i, updatedPlacement);
        }
    }

    public FinalPlacementResponse getFinalPlacement() {
        throw new NotImplementedException();
    }
}
