package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto;
import java.util.List;

public record TreePlacementResponse(
        List<RoundDto> rounds,
        List<TreeMatchDto> matches
) {
}
