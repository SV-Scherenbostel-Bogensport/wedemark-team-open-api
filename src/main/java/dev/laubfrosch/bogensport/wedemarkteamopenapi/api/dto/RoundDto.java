package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Round;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Status;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;

public record RoundDto(
        Integer roundId,
        String description,
        Boolean isKnockOut,
        Status status,
        LocalDateTime updatedAt
) {
    public static RoundDto fromEntity(Round round) {
        Status unproxiedStatus = null;
        if (round.getStatus() != null) {
            unproxiedStatus = (Status) Hibernate.unproxy(round.getStatus());
        }

        return new RoundDto(
                round.getRoundId(),
                round.getDescription(),
                round.getIsKnockOut(),
                unproxiedStatus,
                round.getUpdatedAt()
        );
    }
}