package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Match;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Round;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Status;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {

    @Query("SELECT m.matchId FROM Match m WHERE m.roundId = :roundId ORDER BY m.matchId")
    List<Integer> findMatchIdsByRoundId(@Param("roundId") Integer roundId);

    @Query("SELECT m FROM Match m " +
            "LEFT JOIN FETCH m.round " +
            "LEFT JOIN FETCH m.status " +
            "LEFT JOIN FETCH m.team1 " +
            "LEFT JOIN FETCH m.team2 " +
            "LEFT JOIN FETCH m.target1 " +
            "LEFT JOIN FETCH m.target2 " +
            "WHERE m.matchId = :id")
    Optional<Match> findByIdWithDetails(Integer id);

    @Query("SELECT m FROM Match m WHERE (m.target1.targetId = :targetId OR m.target2.targetId = :targetId) AND m.status.label IN ('ONGOING','PAUSED')")
    Optional<Match> findActiveMatchByTargetId(@Param("targetId") Integer targetId);

    @Query("SELECT m.status FROM Match m WHERE m.round = :round AND (m.target1 = :target OR m.target2 = :target)")
    Optional<Status> findStatusByRoundAndTarget(Round round, Target target);
}
