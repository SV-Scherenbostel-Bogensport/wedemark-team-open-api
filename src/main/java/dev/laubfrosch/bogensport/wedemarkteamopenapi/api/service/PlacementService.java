package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.service;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.FinalPlacementDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.PointsDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.QualificationPlacementDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.TeamDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.enumeration.PlacementJustification;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.util.SqlFileReader;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlacementService {

    private final EntityManager entityManager;

    public PlacementService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<QualificationPlacementDto> getQualificationPlacement(){

        String sql;

        try {
            sql = SqlFileReader.getSqlQueryFromFile("src/main/resources/static/sql/api/getQualificationPlacement.sql");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        List<QualificationPlacementDto> placements = new ArrayList<>();
        int position = 1;

        for (Object[] result : results) {

            Integer place = null;
            PlacementJustification justification = PlacementJustification.MATCH_POINTS;

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
                    place, justification, team, totalMatchPoints, totalSetPoints,
                    averageSetScore * 100.0f / 100.0f
            );

            placements.add(placement);
        }

        return placements;
    }

    public List<FinalPlacementDto> getFinalPlacement() {
        throw new NotImplementedException();
    }
}
