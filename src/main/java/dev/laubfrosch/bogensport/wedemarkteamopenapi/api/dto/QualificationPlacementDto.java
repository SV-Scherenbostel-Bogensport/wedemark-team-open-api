package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.enumeration.PlacementJustification;

public record QualificationPlacementDto(
        Integer place,
        PlacementJustification justification,
        TeamDto team,
        PointsDto totalMatchPoints,
        PointsDto totalSetPoints,
        Float AverageSetScore
) {
}
