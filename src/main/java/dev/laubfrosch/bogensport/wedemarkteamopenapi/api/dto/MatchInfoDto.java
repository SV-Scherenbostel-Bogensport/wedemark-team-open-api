package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Match;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Set;

import java.util.List;

public record MatchInfoDto(Match match, List<Set> sets) {
}
