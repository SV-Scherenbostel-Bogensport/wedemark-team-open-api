package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Set;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Status;

import java.util.List;

public record MatchInfoDto(Integer matchId, String description, TeamDto team1, TeamDto team2, Integer winnerTeamId, String target1Code, String target2Code, Boolean isKnockOut, Status status, List<Set> sets) {
}
