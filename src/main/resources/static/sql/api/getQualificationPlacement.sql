WITH match_points AS (
    SELECT
        t.team_id,
        t.name,
        m.match_id,
        COALESCE(SUM(s.points_team1), 0) as team1_set_points,
        COALESCE(SUM(s.points_team2), 0) as team2_set_points,
        CASE
            WHEN m.match_id IS NULL THEN 0
            WHEN t.team_id = m.team1_id THEN
                CASE
                    WHEN COALESCE(SUM(s.points_team1), 0) > COALESCE(SUM(s.points_team2), 0) THEN 2
                    WHEN COALESCE(SUM(s.points_team1), 0) = COALESCE(SUM(s.points_team2), 0) THEN 1
                    ELSE 0
                    END
            WHEN t.team_id = m.team2_id THEN
                CASE
                    WHEN COALESCE(SUM(s.points_team2), 0) > COALESCE(SUM(s.points_team1), 0) THEN 2
                    WHEN COALESCE(SUM(s.points_team2), 0) = COALESCE(SUM(s.points_team1), 0) THEN 1
                    ELSE 0
                    END
            ELSE 0
            END as match_points,
        CASE
            WHEN m.match_id IS NULL THEN 0
            WHEN t.team_id = m.team1_id THEN COALESCE(SUM(s.points_team1), 0)
            WHEN t.team_id = m.team2_id THEN COALESCE(SUM(s.points_team2), 0)
            ELSE 0
            END as team_set_points,
        CASE
            WHEN m.match_id IS NULL THEN 0
            WHEN t.team_id = m.team1_id THEN COALESCE(SUM(s.points_team2), 0)
            WHEN t.team_id = m.team2_id THEN COALESCE(SUM(s.points_team1), 0)
            ELSE 0
            END as opponent_set_points,
        CASE
            WHEN m.match_id IS NULL THEN 0
            WHEN t.team_id = m.team1_id THEN COALESCE(SUM(s.total_team1), 0)
            WHEN t.team_id = m.team2_id THEN COALESCE(SUM(s.total_team2), 0)
            ELSE 0
            END as total,
        COUNT(s.set_index) as sets_played
    FROM teams t
             LEFT JOIN matches m ON (t.team_id = m.team1_id OR t.team_id = m.team2_id)
        AND m.status_id IN (5, 6)
        AND m.round_id <= 7
             LEFT JOIN sets s ON (m.match_id = s.match_id AND s.set_index <= 5)
    GROUP BY t.team_id, t.name, m.match_id, m.team1_id, m.team2_id
)
SELECT
    team_id,
    name,
    COALESCE(SUM(match_points), 0)                         as total_match_points,
    COALESCE((COUNT(match_id) * 2) - SUM(match_points), 0) as total_match_points_lost,
    COALESCE(SUM(team_set_points), 0)                      as total_set_points,
    COALESCE(SUM(opponent_set_points), 0)                  as total_set_points_lost,
    COUNT(match_id)                                        as matches_played,
    COALESCE(SUM(sets_played), 0)                          as total_sets_played,
    CASE
        WHEN SUM(sets_played) > 0 THEN COALESCE(SUM(total), 0) / SUM(sets_played)
        ELSE 0
        END                                                as avg_set

FROM match_points
GROUP BY team_id, name
ORDER BY COALESCE(SUM(match_points), 0) DESC,
         (COALESCE(SUM(team_set_points), 0) - COALESCE(SUM(opponent_set_points), 0)) DESC