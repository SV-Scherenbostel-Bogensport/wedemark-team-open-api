package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.*;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.enumeration.PlacementJustification;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Round;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository.RoundRepository;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.util.SqlFileReader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.Math.max;

@Service
public class PlacementService {

    private final EntityManager entityManager;
    private final RoundRepository roundRepository;

    public PlacementService(EntityManager entityManager, RoundRepository roundRepository) {
        this.entityManager = entityManager;
        this.roundRepository = roundRepository;
    }

    public QualificationPlacementResponse getQualificationPlacement(){

        Optional<Round> round = roundRepository.getLastFinishedQualificationRound();

        String sql;

        try {
            sql = SqlFileReader.getSqlQueryFromFile("src/main/resources/static/sql/api/getQualificationPlacementData.sql");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("roundId", round.isPresent() ? round.get().getRoundId() : 0);

        @SuppressWarnings("unchecked")
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
        
        this.assignQualificationPlacementsAndJustifications(placements);
        return new QualificationPlacementResponse(round.orElse(null), placements);
    }

    private void assignQualificationPlacementsAndJustifications(List<QualificationPlacementDto> placements) {
        int currentPlace = 1;

        for (int i = 0; i < placements.size(); i++) {
            QualificationPlacementDto current = placements.get(i);
            PlacementJustification justification = PlacementJustification.UNKNOWN;

            // Prüfe, ob es Gleichstände gibt
            if (i > 0) {
                QualificationPlacementDto previous = placements.get(i - 1);

                // Gleiche Matchpunkte?
                if (current.getTotalMatchPoints().won().equals(previous.getTotalMatchPoints().won())) {

                    // Gleiche Satzdifferenz?
                    int currentSetDiff = current.getTotalSetPoints().won() - current.getTotalSetPoints().lost();
                    int previousSetDiff = previous.getTotalSetPoints().won() - previous.getTotalSetPoints().lost();

                    if (currentSetDiff == previousSetDiff) {
                        currentPlace = previous.getPlace();
                    }
                } else {
                    currentPlace = i + 1;
                }
            }

            placements.get(i).setPlace(currentPlace);
            placements.get(i).setJustification(justification);
        }
    }


    public FinalPlacementResponse getFinalPlacement() {
        Optional<Round> round = roundRepository.getLastFinishedFinalRound();

        if (round.isEmpty()) {
            if (!roundRepository.hasQualificationFinished().orElse(false)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Es existiert noch kein Leaderboard für die Finalrunde");
            }
            // Mit Round.isEmpty wird jetzt einfach eine Startliste geladen.
        }

        Map<Integer, Integer> qualificationPlacements = getQualificationPlacementsMap();

        String sql;

        try {
            sql = SqlFileReader.getSqlQueryFromFile("src/main/resources/static/sql/api/getFinalPlacementData.sql");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("roundId", round.isPresent() ? round.get().getRoundId() : 0);

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        List<FinalPlacementDto> placements = new ArrayList<>();

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

            Integer qualificationPlace = qualificationPlacements.get(team.teamId());

            int MAX_LIVES = 2;
            FinalPlacementDto placement = new FinalPlacementDto(
                    null,
                    PlacementJustification.UNKNOWN,
                    qualificationPlace,
                    team,
                    totalMatchPoints,
                    totalSetPoints,
                    averageSetScore,
                    max(MAX_LIVES - totalMatchPoints.lost()/2, 0)
            );

            placements.add(placement);
        }

        this.assignFinalPlacementsAndJustifications(placements);
        return new FinalPlacementResponse(round.orElse(null), placements);
    }

    private void assignFinalPlacementsAndJustifications(List<FinalPlacementDto> placements) {
        String sql;
        try {
            sql = SqlFileReader.getSqlQueryFromFile("src/main/resources/static/sql/api/getFinalPlacement.sql");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Query query = entityManager.createNativeQuery(sql);

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        Map<Integer, FinalPlacementDto> placementMap = placements.stream()
                .collect(Collectors.toMap(p -> p.getTeam().teamId(), p -> p));

        for (Object[] result : results) {
            Integer teamIdFromDb = ((Number) result[1]).intValue();
            int placeFromDb = ((Number) result[0]).intValue();

            FinalPlacementDto dto = placementMap.get(teamIdFromDb);
            if (dto != null) {
                dto.setPlace(placeFromDb);
            }
        }

        AtomicInteger index = new AtomicInteger(0);
        Map<FinalPlacementDto, Integer> originalIndices = placements.stream()
                .collect(Collectors.toMap(Function.identity(), p -> index.getAndIncrement()));

        placements.sort((dto1, dto2) -> {
            Integer place1 = dto1.getPlace();
            Integer place2 = dto2.getPlace();

            if (place1 == null && place2 == null) {
                return Integer.compare(originalIndices.get(dto1), originalIndices.get(dto2));
            }
            if (place1 == null) return -1;
            if (place2 == null) return 1;
            return place1.compareTo(place2);
        });

        // TODO: Falls für ein placement Platz -> leben -> satzpunkte gleich ist, soll als lletztes kriterium noch nach quali platzierung sortiert werden
    }

    private Map<Integer, Integer> getQualificationPlacementsMap() {
        Optional<Round> qualRound = roundRepository.getLastFinishedQualificationRound();

        if (qualRound.isEmpty()) {
            return Map.of();
        }

        String sql;
        try {
            sql = SqlFileReader.getSqlQueryFromFile("src/main/resources/static/sql/api/getQualificationPlacementData.sql");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("roundId", qualRound.get().getRoundId());

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        List<QualificationPlacementDto> placements = new ArrayList<>();

        for (Object[] result : results) {
            TeamDto team = new TeamDto(
                    ((Number) result[0]).intValue(),
                    result[1].toString()
            );

            PointsDto totalMatchPoints = new PointsDto(
                    ((Number) result[2]).intValue(),
                    ((Number) result[3]).intValue()
            );

            PointsDto totalSetPoints = new PointsDto(
                    ((Number) result[4]).intValue(),
                    ((Number) result[5]).intValue()
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

        // Platzierungen zuweisen
        this.assignQualificationPlacementsAndJustifications(placements);

        // In eine Map umwandeln: TeamId -> Platzierung
        return placements.stream()
                .collect(Collectors.toMap(
                        p -> p.getTeam().teamId(),
                        QualificationPlacementDto::getPlace
                ));
    }
}
