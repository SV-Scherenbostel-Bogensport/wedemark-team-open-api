package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto;

public record CreateArrowDto(String targetCode, Integer roundId, Integer squadNumber, Integer setIndex, Integer arrowIndex) {
}

