package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto;

public record AttendeesDto (Integer teamId, String name, Integer playerCount, boolean lookingForTeammates, boolean hasPayed) {
}