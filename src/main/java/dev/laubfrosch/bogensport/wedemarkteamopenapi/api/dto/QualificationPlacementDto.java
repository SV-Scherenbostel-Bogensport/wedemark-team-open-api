package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.enumeration.PlacementJustification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QualificationPlacementDto{

    private Integer place;
    private PlacementJustification justification;
    private TeamDto team;
    private PointsDto totalMatchPoints;
    private PointsDto totalSetPoints;
    private Float averageSetScore;
}
