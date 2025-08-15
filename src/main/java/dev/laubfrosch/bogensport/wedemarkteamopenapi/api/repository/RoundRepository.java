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
    Optional<Round> getLatestFinishedRound();

    @Query("SELECT r FROM RoundOverview ro INNER JOIN Status s ON s.statusId = ro.statusId INNER JOIN Round r ON r.roundId = ro.roundId WHERE s.label in ('ENDED', 'CANCELED') AND r.isKnockOut = false ORDER BY r.roundId DESC LIMIT 1")
    Optional<Round> getLatestFinishedQualificationRound();

    @Query("SELECT r FROM RoundOverview ro INNER JOIN Status s ON s.statusId = ro.statusId INNER JOIN Round r ON r.roundId = ro.roundId WHERE s.label in ('ENDED', 'CANCELED') AND r.isKnockOut = true ORDER BY r.roundId DESC LIMIT 1")
    Optional<Round> getLatestFinishedFinalRound();

    @Query("""
        SELECT r
        FROM RoundOverview ro
        JOIN Status s ON s.statusId = ro.statusId
        JOIN Round r ON r.roundId = ro.roundId
        WHERE s.label IN ('ENDED', 'CANCELED')
          AND NOT EXISTS (
              SELECT 1
              FROM RoundOverview ro2
              JOIN Status s2 ON s2.statusId = ro2.statusId
              JOIN Round r2 ON r2.roundId = ro2.roundId
              WHERE r2.roundId < r.roundId
                AND s2.label IN ('ONGOING', 'PAUSED')
          )
        ORDER BY r.roundId DESC
        LIMIT 1
    """)
    Optional<Round> getLatestFinishedRoundWithoutActiveBefore();

    @Query("""
        SELECT r
        FROM RoundOverview ro
        JOIN Status s ON s.statusId = ro.statusId
        JOIN Round r ON r.roundId = ro.roundId
        WHERE s.label IN ('ENDED', 'CANCELED')
          AND r.isKnockOut = false
          AND NOT EXISTS (
              SELECT 1
              FROM RoundOverview ro2
              JOIN Status s2 ON s2.statusId = ro2.statusId
              JOIN Round r2 ON r2.roundId = ro2.roundId
              WHERE r2.roundId < r.roundId
                AND s2.label IN ('ONGOING', 'PAUSED')
          )
        ORDER BY r.roundId DESC
        LIMIT 1
    """)
    Optional<Round> getLatestFinishedQualificationRoundWithoutActiveBefore();

    @Query("""
        SELECT r
        FROM RoundOverview ro
        JOIN Status s ON s.statusId = ro.statusId
        JOIN Round r ON r.roundId = ro.roundId
        WHERE s.label IN ('ENDED', 'CANCELED')
          AND r.isKnockOut = true
          AND NOT EXISTS (
              SELECT 1
              FROM RoundOverview ro2
              JOIN Status s2 ON s2.statusId = ro2.statusId
              JOIN Round r2 ON r2.roundId = ro2.roundId
              WHERE r2.roundId < r.roundId
                AND s2.label IN ('ONGOING', 'PAUSED')
          )
        ORDER BY r.roundId DESC
        LIMIT 1
    """)
    Optional<Round> getLatestFinishedFinalRoundWithoutActiveBefore();


    @Query("SELECT CASE WHEN COUNT(*) = COUNT(CASE WHEN s.label IN ('ENDED', 'CANCELED') THEN 1 END) THEN TRUE ELSE FALSE END AS is_qualification_finished FROM RoundOverview ro INNER JOIN Status s ON s.statusId = ro.statusId INNER JOIN Round r ON r.roundId = ro.roundId WHERE r.isKnockOut = FALSE")
    Optional<Boolean> hasQualificationFinished();
}
