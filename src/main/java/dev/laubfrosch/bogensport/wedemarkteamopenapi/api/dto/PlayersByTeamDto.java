package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Player;

import java.util.List;

public record PlayersByTeamDto(Integer teamId, String teamName, List<Player> players) {
}
