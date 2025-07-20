package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.enumeration.PlacementJustification;

public record FinalPlacementDto(
        Integer place,
        Integer qualificationPlace,
        TeamDto team,
        PointsDto totalSetPoints,
        PointsDto totalMatchPoints,
        Integer lives,
        PlacementJustification justification,
        Float AverageSet
) {
}
