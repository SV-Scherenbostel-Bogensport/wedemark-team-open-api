package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto;

public record TeamDto(Integer teamId, String name, boolean lookingForTeammates, boolean hasPayed) {
}