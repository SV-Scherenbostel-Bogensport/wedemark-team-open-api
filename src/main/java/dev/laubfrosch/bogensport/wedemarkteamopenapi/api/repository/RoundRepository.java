package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.dto.GetTeamIdsDto;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoundRepository extends JpaRepository<Round, Integer> {

    List<Round> findByIsKnockOutFalse();

    List<Round> findByIsKnockOutTrue();

    @Query("SELECT m.roundId FROM Match m INNER JOIN Status s ON s.statusId = m.statusId WHERE s.label in ('ONGOING', 'PAUSED') ORDER BY m.roundId LIMIT 1")
    Optional<Integer> findActiveRound();

    @Query("SELECT m.roundId FROM Match m INNER JOIN Status s ON s.statusId = m.statusId WHERE s.label in ('UPCOMING') ORDER BY m.roundId LIMIT 1")
    Optional<Integer> findNextActiveRound();

    @Query("SELECT m.roundId FROM Match m INNER JOIN Status s ON s.statusId = m.statusId WHERE s.label in ('ENDED', 'CANCELED') ORDER BY m.roundId ASC LIMIT 1")
    Optional<Integer> findLastActiveRound();
}
