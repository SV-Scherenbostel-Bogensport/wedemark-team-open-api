package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Round;

import java.util.List;

public record TreePlacementResponse(
        List<Round> rounds,
        List<TreeMatchDto> matches
) {
}
