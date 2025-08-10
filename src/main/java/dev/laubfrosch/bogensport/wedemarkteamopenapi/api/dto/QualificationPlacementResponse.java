package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Round;

import java.util.List;

public record QualificationPlacementResponse(
        Round round,
        List<QualificationPlacementDto> qualificationPlacements
) {
}
