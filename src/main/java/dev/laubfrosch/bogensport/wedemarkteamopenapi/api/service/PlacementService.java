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
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.max;

@Service
public class PlacementService {

    private static final int MAX_LIVES = 2;
    private final EntityManager entityManager;
    private final RoundRepository roundRepository;

    public PlacementService(EntityManager entityManager, RoundRepository roundRepository) {
        this.entityManager = entityManager;
        this.roundRepository = roundRepository;
    }

    public QualificationPlacementResponse getQualificationPlacement() {
        Optional<Round> round = roundRepository.getLastFinishedQualificationRound();
        List<QualificationPlacementDto> placements = loadQualificationPlacements(round);
        assignQualificationPlacementsAndJustifications(placements);
        return new QualificationPlacementResponse(round.orElse(null), placements);
    }

    public FinalPlacementResponse getFinalPlacement() {
        Optional<Round> round = roundRepository.getLastFinishedFinalRound();

        if (round.isEmpty() && !roundRepository.hasQualificationFinished().orElse(false)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Es existiert noch kein Leaderboard für die Finalrunde");
        }

        Map<Integer, Integer> qualificationPlacements = getQualificationPlacementsMap();
        List<FinalPlacementDto> placements = loadFinalPlacements(round, qualificationPlacements);
        assignFinalPlacementsAndJustifications(placements);

        return new FinalPlacementResponse(round.orElse(null), placements);
    }

    private List<QualificationPlacementDto> loadQualificationPlacements(Optional<Round> round) {
        String sql = loadSqlFile("getQualificationPlacementData.sql");
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("roundId", round.map(Round::getRoundId).orElse(0));

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        return results.stream()
                .map(this::mapToQualificationPlacement)
                .collect(Collectors.toList());
    }

    private List<FinalPlacementDto> loadFinalPlacements(Optional<Round> round, Map<Integer, Integer> qualificationPlacements) {
        String sql = loadSqlFile("getFinalPlacementData.sql");
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("roundId", round.map(Round::getRoundId).orElse(0));

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        return results.stream()
                .map(result -> mapToFinalPlacement(result, qualificationPlacements))
                .collect(Collectors.toList());
    }

    private QualificationPlacementDto mapToQualificationPlacement(Object[] result) {
        PlacementData data = extractPlacementData(result);

        return new QualificationPlacementDto(
                null,
                PlacementJustification.UNKNOWN,
                data.team(),
                data.totalMatchPoints(),
                data.totalSetPoints(),
                data.averageSetScore()
        );
    }

    private FinalPlacementDto mapToFinalPlacement(Object[] result, Map<Integer, Integer> qualificationPlacements) {
        PlacementData data = extractPlacementData(result);
        Integer qualificationPlace = qualificationPlacements.get(data.team().teamId());
        int lives = max(MAX_LIVES - data.totalMatchPoints().lost() / 2, 0);

        return new FinalPlacementDto(
                null,
                PlacementJustification.UNKNOWN,
                qualificationPlace,
                data.team(),
                data.totalMatchPoints(),
                data.totalSetPoints(),
                data.averageSetScore(),
                lives
        );
    }

    private PlacementData extractPlacementData(Object[] result) {
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

        return new PlacementData(team, totalMatchPoints, totalSetPoints, averageSetScore);
    }

    private record PlacementData(
            TeamDto team,
            PointsDto totalMatchPoints,
            PointsDto totalSetPoints,
            float averageSetScore
    ) {}

    private void assignQualificationPlacementsAndJustifications(List<QualificationPlacementDto> placements) {
        int currentPlace = 1;

        for (int i = 0; i < placements.size(); i++) {
            QualificationPlacementDto current = placements.get(i);

            if (i > 0) {
                QualificationPlacementDto previous = placements.get(i - 1);

                if (!hasSameQualificationRanking(current, previous)) {
                    currentPlace = i + 1;
                }
            }

            current.setPlace(currentPlace);
            current.setJustification(PlacementJustification.UNKNOWN);
        }
    }

    private boolean hasSameQualificationRanking(QualificationPlacementDto current, QualificationPlacementDto previous) {
        // Gleiche Matchpunkte?
        if (!current.getTotalMatchPoints().won().equals(previous.getTotalMatchPoints().won())) {
            return false;
        }

        // Gleiche Satzdifferenz?
        int currentSetDiff = current.getTotalSetPoints().won() - current.getTotalSetPoints().lost();
        int previousSetDiff = previous.getTotalSetPoints().won() - previous.getTotalSetPoints().lost();

        return currentSetDiff == previousSetDiff;
    }

    private void assignFinalPlacementsAndJustifications(List<FinalPlacementDto> placements) {
        Map<Integer, Integer> finalPlacementsFromDb = loadFinalPlacementsFromDatabase();

        // Plätze aus der Datenbank zuweisen
        for (FinalPlacementDto placement : placements) {
            Integer place = finalPlacementsFromDb.get(placement.getTeam().teamId());
            if (place != null) {
                placement.setPlace(place);
            }
        }

        // Sortierung: Platzierte zuerst, dann unplatzierte in ursprünglicher Reihenfolge
        List<FinalPlacementDto> sortedPlacements = new ArrayList<>();

        // Erst die platzierten Teams, sortiert nach Platz
        placements.stream()
                .filter(p -> p.getPlace() != null)
                .sorted(Comparator.comparing(FinalPlacementDto::getPlace))
                .forEach(sortedPlacements::add);

        // Dann die unplatzierten Teams in ursprünglicher Reihenfolge
        // sortiert nach Leben → Satzpunkten → Qualifikationsplatz
        placements.stream()
                .filter(p -> p.getPlace() == null)
                .sorted(this::compareFinalPlacementsForTieBreaker)
                .forEach(sortedPlacements::add);

        placements.clear();
        placements.addAll(sortedPlacements);
    }

    private int compareFinalPlacementsForTieBreaker(FinalPlacementDto dto1, FinalPlacementDto dto2) {
        // 1. Leben (mehr ist besser)
        int livesComparison = Integer.compare(dto2.getLives(), dto1.getLives());
        if (livesComparison != 0) {
            return livesComparison;
        }

        // 2. Satzpunktedifferenz (höher ist besser)
        int setDiff1 = dto1.getTotalSetPoints().won() - dto1.getTotalSetPoints().lost();
        int setDiff2 = dto2.getTotalSetPoints().won() - dto2.getTotalSetPoints().lost();
        int setDiffComparison = Integer.compare(setDiff2, setDiff1);
        if (setDiffComparison != 0) {
            return setDiffComparison;
        }

        // 3. Qualifikationsplatz (niedriger ist besser)
        if (dto1.getQualificationPlace() != null && dto2.getQualificationPlace() != null) {
            return Integer.compare(dto1.getQualificationPlace(), dto2.getQualificationPlace());
        }

        // Falls einer null ist, den mit Wert bevorzugen
        if (dto1.getQualificationPlace() != null) return -1;
        if (dto2.getQualificationPlace() != null) return 1;

        return 0;
    }

    private Map<Integer, Integer> loadFinalPlacementsFromDatabase() {
        String sql = loadSqlFile("getFinalPlacement.sql");
        Query query = entityManager.createNativeQuery(sql);

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        return results.stream()
                .collect(Collectors.toMap(
                        result -> ((Number) result[1]).intValue(), // teamId
                        result -> ((Number) result[0]).intValue()  // place
                ));
    }

    private Map<Integer, Integer> getQualificationPlacementsMap() {
        Optional<Round> qualRound = roundRepository.getLastFinishedQualificationRound();

        if (qualRound.isEmpty()) {
            return Map.of();
        }

        List<QualificationPlacementDto> placements = loadQualificationPlacements(qualRound);
        assignQualificationPlacementsAndJustifications(placements);

        return placements.stream()
                .collect(Collectors.toMap(
                        p -> p.getTeam().teamId(),
                        QualificationPlacementDto::getPlace
                ));
    }

    private String loadSqlFile(String filename) {
        try {
            return SqlFileReader.getSqlQueryFromFile("src/main/resources/static/sql/api/" + filename);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load SQL file: " + filename, e);
        }
    }
}