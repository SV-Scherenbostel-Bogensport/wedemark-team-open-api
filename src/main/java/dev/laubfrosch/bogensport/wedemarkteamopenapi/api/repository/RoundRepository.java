package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository;

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


    @Query("SELECT r FROM RoundOverview ro INNER JOIN Status s ON s.statusId = ro.statusId INNER JOIN Round r ON r.roundId = ro.roundId WHERE s.label in ('ONGOING', 'PAUSED') ORDER BY r.roundId ASC LIMIT 1")
    Optional<Round> getCurrentRound();


    @Query("SELECT r FROM RoundOverview ro INNER JOIN Status s ON s.statusId = ro.statusId INNER JOIN Round r ON r.roundId = ro.roundId WHERE s.label in ('UPCOMING') ORDER BY r.roundId ASC LIMIT 1")
    Optional<Round> getNextUpcomingRound();


    @Query("SELECT r FROM RoundOverview ro INNER JOIN Status s ON s.statusId = ro.statusId INNER JOIN Round r ON r.roundId = ro.roundId WHERE s.label in ('ENDED', 'CANCELED') ORDER BY r.roundId DESC LIMIT 1")
    Optional<Round> getLastFinishedRound();

    @Query("SELECT r FROM RoundOverview ro INNER JOIN Status s ON s.statusId = ro.statusId INNER JOIN Round r ON r.roundId = ro.roundId WHERE s.label in ('ENDED', 'CANCELED') AND r.isKnockOut = false ORDER BY r.roundId DESC LIMIT 1")
    Optional<Round> getLastFinishedQualificationRound();

    @Query("SELECT r FROM RoundOverview ro INNER JOIN Status s ON s.statusId = ro.statusId INNER JOIN Round r ON r.roundId = ro.roundId WHERE s.label in ('ENDED', 'CANCELED') AND r.isKnockOut = true ORDER BY r.roundId DESC LIMIT 1")
    Optional<Round> getLastFinishedFinalRound();


    @Query("SELECT CASE WHEN COUNT(*) = COUNT(CASE WHEN s.label IN ('ENDED', 'CANCELED') THEN 1 END) THEN TRUE ELSE FALSE END AS is_qualification_finished FROM RoundOverview ro INNER JOIN Status s ON s.statusId = ro.statusId INNER JOIN Round r ON r.roundId = ro.roundId WHERE r.isKnockOut = FALSE")
    Optional<Boolean> hasQualificationFinished();
}
