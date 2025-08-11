package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Round;

import java.util.List;

public record FinalPlacementResponse(
        Round round,
        List<FinalPlacementDto> finalPlacements
) {
}
