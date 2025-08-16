package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Status;
import org.hibernate.Hibernate;

public record TreeMatchDto(
        Integer matchId,
        Status status,
        String description,
        String team1targetCode,
        String team2targetCode,
        String team1Name,
        String team2Name,
        Integer team1MatchPoints,
        Integer team2MatchPoints
) {
    public static TreeMatchDto fromMatchInfo(MatchInfoDto matchInfo) {
        return new TreeMatchDto(
                matchInfo.matchId(),
                (Status) Hibernate.unproxy(matchInfo.status()),
                matchInfo.description(),
                matchInfo.target1Code(),
                matchInfo.target2Code(),
                matchInfo.team1() != null ? matchInfo.team1().name() : "",
                matchInfo.team2() != null ? matchInfo.team2().name() : "",
                matchInfo.sets().stream().mapToInt(set -> set.getPointsTeam1() != null ? set.getPointsTeam1() : 0).sum(),
                matchInfo.sets().stream().mapToInt(set -> set.getPointsTeam2() != null ? set.getPointsTeam2() : 0).sum()
        );
    }
}
