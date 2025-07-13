package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto;

import java.util.List;

public record RoundMatchIdsResponse(Integer roundId, List<Integer> matchIds) {
}
