
SELECT
    8 AS final_place,
    CASE
        WHEN SUM(s.points_team1) > SUM(s.points_team2) AND m.status_id IN (5,6) THEN m.team2_id
        WHEN SUM(s.points_team1) < SUM(s.points_team2) AND m.status_id IN (5,6) THEN m.team1_id
        END AS team_id
FROM Matches m
         JOIN Sets s ON s.match_id = m.match_id
WHERE m.match_Id = 39
  AND m.round_id <= :roundId
GROUP BY m.team1_id, m.team2_id, m.match_id, m.status_id

UNION ALL

SELECT
    7 AS final_place,
    CASE
        WHEN SUM(s.points_team1) > SUM(s.points_team2) AND m.status_id IN (5,6) THEN m.team1_id
        WHEN SUM(s.points_team1) < SUM(s.points_team2) AND m.status_id IN (5,6) THEN m.team2_id
        END AS team_id
FROM Matches m
         JOIN Sets s ON s.match_id = m.match_Id
WHERE m.match_Id = 39
  AND m.round_id <= :roundId
GROUP BY m.team1_id, m.team2_id, m.match_id, m.status_id

UNION ALL

SELECT
    6 AS final_place,
    CASE
        WHEN SUM(s.points_team1) > SUM(s.points_team2) AND m.status_id IN (5,6) THEN m.team2_id
        WHEN SUM(s.points_team1) < SUM(s.points_team2) AND m.status_id IN (5,6) THEN m.team1_id
        END AS team_id
FROM Matches m
         JOIN Sets s ON s.match_id = m.match_Id
WHERE m.match_Id = 42
  AND m.round_id <= :roundId
GROUP BY m.team1_id, m.team2_id, m.match_id, m.status_id

UNION ALL

SELECT
    5 AS final_place,
    CASE
        WHEN SUM(s.points_team1) > SUM(s.points_team2) AND m.status_id IN (5,6) THEN m.team1_id
        WHEN SUM(s.points_team1) < SUM(s.points_team2) AND m.status_id IN (5,6) THEN m.team2_id
        END AS team_id
FROM Matches m
         JOIN Sets s ON s.match_id = m.match_Id
WHERE m.match_Id = 42
  AND m.round_id <= :roundId
GROUP BY m.team1_id, m.team2_id, m.match_id, m.status_id

UNION ALL

SELECT
    4 AS final_place,
    CASE
        WHEN SUM(s.points_team1) > SUM(s.points_team2) AND m.status_id IN (5,6) THEN m.team2_id
        WHEN SUM(s.points_team1) < SUM(s.points_team2) AND m.status_id IN (5,6) THEN m.team1_id
        END AS team_id
FROM Matches m
         JOIN Sets s ON s.match_id = m.match_Id
WHERE m.match_Id = 41
  AND m.round_id <= :roundId
GROUP BY m.team1_id, m.team2_id, m.match_id, m.status_id

UNION ALL

SELECT
    3 AS final_place,
    CASE
        WHEN SUM(s.points_team1) > SUM(s.points_team2) AND m.status_id IN (5,6) THEN m.team2_id
        WHEN SUM(s.points_team1) < SUM(s.points_team2) AND m.status_id IN (5,6) THEN m.team1_id
        END AS team_id
FROM Matches m
         JOIN Sets s ON s.match_id = m.match_Id
WHERE m.match_Id = 43
  AND m.round_id <= :roundId
GROUP BY m.team1_id, m.team2_id, m.match_id, m.status_id

UNION ALL

SELECT
    2 AS final_place,
    CASE
        WHEN SUM(s.points_team1) > SUM(s.points_team2) AND m.status_id IN (5,6) THEN m.team2_id
        WHEN SUM(s.points_team1) < SUM(s.points_team2) AND m.status_id IN (5,6) THEN (

            SELECT
                CASE
                    WHEN SUM(s.points_team1) > SUM(s.points_team2) AND m.status_id IN (5,6) THEN m.team2_id
                    WHEN SUM(s.points_team1) < SUM(s.points_team2) AND m.status_id IN (5,6) THEN m.team1_id
                    END AS team_id
            FROM Matches m
                     JOIN Sets s ON s.match_id = m.match_Id
            WHERE m.match_Id = 45
              AND m.round_id <= :roundId
            GROUP BY m.team1_id, m.team2_id, m.match_id, m.status_id

        )
        END AS team_id
FROM Matches m
         JOIN Sets s ON s.match_id = m.match_Id
WHERE m.match_Id = 44
  AND m.round_id <= :roundId
GROUP BY m.team1_id, m.team2_id, m.match_id, m.status_id

UNION ALL

SELECT
    1 AS final_place,
    CASE
        WHEN SUM(s.points_team1) > SUM(s.points_team2) AND m.status_id IN (5,6) THEN m.team1_id
        WHEN SUM(s.points_team1) < SUM(s.points_team2) AND m.status_id IN (5,6) THEN (

            SELECT
                CASE
                    WHEN SUM(s.points_team1) > SUM(s.points_team2) AND m.status_id IN (5,6) THEN m.team1_id
                    WHEN SUM(s.points_team1) < SUM(s.points_team2) AND m.status_id IN (5,6) THEN m.team2_id
                    END AS team_id
            FROM Matches m
                     JOIN Sets s ON s.match_id = m.match_Id
            WHERE m.match_Id = 45
              AND m.round_id <= :roundId
            GROUP BY m.team1_id, m.team2_id, m.match_id, m.status_id

        )
        END AS team_id
FROM Matches m
         JOIN Sets s ON s.match_id = m.match_Id
WHERE m.match_Id = 44
  AND m.round_id <= :roundId
GROUP BY m.team1_id, m.team2_id, m.match_id, m.status_id;


