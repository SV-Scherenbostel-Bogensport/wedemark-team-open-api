package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Status;

public record TreeMatchDto(
        Integer matchId,
        Status status,
        String team1targetCode,
        String team2targetCode,
        String team1Name,
        String team2Name,
        Integer team1MatchPoints,
        Integer team2MatchPoints
) {
}
