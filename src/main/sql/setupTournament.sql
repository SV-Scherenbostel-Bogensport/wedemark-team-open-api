-- Datenbank sollte beim Setup neu und leer sein,
-- damit die Runden und Match und Team Ids verwendet werden können

-- Status
INSERT INTO status
    (label, description, primary_color, secondary_color, pulsing)
VALUES
    ('UNKNOWN', '???', null, null, false),
    ('UPCOMING', 'anstehend', '#1b58ff', null, false),
    ('ONGOING', 'laufend', '#00ce22', '#00ce22', true),
    ('PAUSED', 'pausiert', '#ff9900', '#ff9900', true),
    ('ENDED', 'beendet', '#878787', null, false),
    ('CANCELED', 'abgebrochen', '#ff0000', null, false);


-- Targets
INSERT INTO targets
    (code)
VALUES
    ('1'),
    ('2'),
    ('3'),
    ('4'),
    ('5'),
    ('6'),
    ('7'),
    ('8');


-- Teams
INSERT INTO teams
    (name, contact_email, player_count, has_payed, looking_for_teammates)
VALUES
    ('SV Scherenbostel', null, null, true, false),
    ('Schützen-Corps Lehrte', null, null, true, false),
    ('MTV Ohndorf', null, null, true, false),
    ('BSV Helstorf', null, null, true, false),
    ('KKS Falkenauge Edemissen', null, null, true, true),
    ('BSC Clauen', null, null, true, false),
    ('SV Gümmer', null, null, true, false),
    ('VfL Grasdorf', null, null, true, false);


-- Players
INSERT INTO players
    (team_id, squad_number, first_name, last_name)
VALUES
    (1, 1, '', '');


-- Rounds
INSERT INTO rounds
    (status_id, description, is_knock_out)
VALUES
    (2, '1. Qualifikationsrunde', false),
    (2, '2. Qualifikationsrunde', false),
    (2, '3. Qualifikationsrunde', false),
    (2, '4. Qualifikationsrunde', false),
    (2, '5. Qualifikationsrunde', false),
    (2, '6. Qualifikationsrunde', false),
    (2, '7. Qualifikationsrunde', false),
    (2, '1. Finalrunde', true),
    (2, '2. Finalrunde', true),
    (2, '3. Finalrunde', true),
    (2, '4. Finalrunde', true),
    (2, '5. Finalrunde', true),
    (2, '6. Finalrunde', true),
    (2, '7. Finalrunde', true);


-- Matches
INSERT INTO matches
    (round_id, status_id, description, team1_id, team2_id, winner_team_id, target1_id, target2_id)
VALUES
    (1, 2, '1. Qualifikationsmatch', 5, 4, null, 1, 2),
    (1, 2, '1. Qualifikationsmatch', 2, 7, null, 3, 4),
    (1, 2, '1. Qualifikationsmatch', 1, 8, null, 5, 6),
    (1, 2, '1. Qualifikationsmatch', 3, 6, null, 7, 8),

    (2, 2, '2. Qualifikationsmatch', 3, 5, null, 1, 2),
    (2, 2, '2. Qualifikationsmatch', 8, 4, null, 3, 4),
    (2, 2, '2. Qualifikationsmatch', 7, 1, null, 5, 6),
    (2, 2, '2. Qualifikationsmatch', 6, 2, null, 7, 8),

    (3, 2, '3. Qualifikationsmatch', 4, 7, null, 1, 2),
    (3, 2, '3. Qualifikationsmatch', 1, 6, null, 3, 4),
    (3, 2, '3. Qualifikationsmatch', 2, 5, null, 5, 6),
    (3, 2, '3. Qualifikationsmatch', 8, 3, null, 7, 8),

    (4, 2, '4. Qualifikationsmatch', 8, 2, null, 1, 2),
    (4, 2, '4. Qualifikationsmatch', 7, 3, null, 3, 4),
    (4, 2, '4. Qualifikationsmatch', 6, 4, null, 5, 6),
    (4, 2, '4. Qualifikationsmatch', 1, 5, null, 7, 8),

    (5, 2, '5. Qualifikationsmatch', 7, 6, null, 1, 2),
    (5, 2, '5. Qualifikationsmatch', 5, 8, null, 3, 4),
    (5, 2, '5. Qualifikationsmatch', 3, 2, null, 5, 6),
    (5, 2, '5. Qualifikationsmatch', 4, 1, null, 7, 8),

    (6, 2, '6. Qualifikationsmatch', 1, 3, null, 1, 2),
    (6, 2, '6. Qualifikationsmatch', 4, 2, null, 3, 4),
    (6, 2, '6. Qualifikationsmatch', 8, 6, null, 5, 6),
    (6, 2, '6. Qualifikationsmatch', 5, 7, null, 7, 8),

    (7, 2, '7. Qualifikationsmatch', 2, 1, null, 1, 2),
    (7, 2, '7. Qualifikationsmatch', 6, 5, null, 3, 4),
    (7, 2, '7. Qualifikationsmatch', 4, 3, null, 5, 6),
    (7, 2, '7. Qualifikationsmatch', 7, 8, null, 7, 8),

    (8, 2, '[Ko1] Winners Bracket: Eingangsrunde: Q4 vs Q5', null, null, null, 1, 2),
    (8, 2, '[Ko2] Winners Bracket: Eingangsrunde: Q2 vs Q7', null, null, null, 3, 4),
    (8, 2, '[Ko3] Winners Bracket: Eingangsrunde: Q1 vs Q8', null, null, null, 5, 6),
    (8, 2, '[Ko4] Winners Bracket: Eingangsrunde: Q3 vs Q6', null, null, null, 7, 8),

    (9, 2, '[Ko5] Losers Bracket: L-Ko1 vs L-Ko2', null, null, null, 1, 2),
    (9, 2, '[Ko6] Winners Bracket: W-Ko1 vs W-Ko2', null, null, null, 3, 4),
    (9, 2, '[Ko7] Winners Bracket: W-Ko3 vs W-Ko4', null, null, null, 5, 6),
    (9, 2, '[Ko8] Losers Bracket: L-Ko3 vs L-Ko4', null, null, null, 7, 8),

    (10, 2, '[Ko9] Losers Bracket: Konsolidierungsrunde: W-Ko8 vs L-Ko6', null, null, null, 1, 2),
    (10, 2, '[Ko10] Losers Bracket: Konsolidierungsrunde: W-Ko5 vs L-Ko7', null, null, null, 5, 6),
    (10, 2, '[Ko11] Losers Bracket: Spiel um Platz 7 & 8: L-Ko8 vs L-Ko5', null, null, null, 3, 4),

    (11, 2, '[Ko12] Winners Bracket: W-Ko7 vs W-Ko6', null, null, null, 1, 2),
    (11, 2, '[Ko13] Losers Bracket: W-Ko9 vs W-Ko10', null, null, null, 5, 6),
    (11, 2, '[Ko14] Losers Bracket: Spiel um Platz 5 & 6: L-Ko9 vs L-Ko10', null, null, null, 3, 4),

    (12, 2, '[Ko15] Losers Bracket: Spiel um Platz 3: W-Ko13 vs L-Ko12', null, null, null, 3, 4),

    (13, 2, '[Ko16] Finale: W-Ko12 vs L-Ko15', null, null, null, 3, 4),

    (14, 1, '[Ko17] 2. Finale: W-Ko16 vs L-Ko16', null, null, null, 3, 4);


-- Sets
-- kein Setup


-- Scores
INSERT INTO scores
    (score_code, value, color)
VALUES
    ('M', 0, '#161616'),
    ('6', 6, '#3535ff'),
    ('7', 7, '#f73337'),
    ('8', 8, '#f73337'),
    ('9', 9, '#f7ce33'),
    ('10', 10, '#f7ce33'),
    ('X', 10, '#f7ce33');


-- Arrows
-- kein Setup
