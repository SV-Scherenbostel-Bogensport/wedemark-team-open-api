package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto;

public record TeamDto(Integer teamId, String name, Integer playerCount, boolean lookingForTeammates, boolean hasPayed) {
}