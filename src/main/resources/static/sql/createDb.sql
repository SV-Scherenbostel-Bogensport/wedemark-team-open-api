-- Status
CREATE TABLE status (
    status_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    label VARCHAR(128) NOT NULL UNIQUE,
    description TEXT,
    primary_color CHAR(7) CHECK (primary_color ~ '^#[0-9A-Fa-f]{6}$'),
    secondary_color CHAR(7) CHECK (secondary_color ~ '^#[0-9A-Fa-f]{6}$'),
    pulsing BOOLEAN NOT NULL DEFAULT FALSE
);

-- Targets
CREATE TABLE targets (
    target_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code VARCHAR(16) NOT NULL UNIQUE
);

-- Teams
CREATE TABLE teams (
    team_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contact_email VARCHAR(255),
    player_count SMALLINT,
    has_payed BOOLEAN NOT NULL DEFAULT FALSE,
    looking_for_teammates BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT now()
);

-- Players
CREATE TABLE players (
    player_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    team_id INT NOT NULL REFERENCES teams(team_id),
    squad_number SMALLINT,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    CONSTRAINT unique_team_squad UNIQUE (team_id, squad_number)
);

-- Rounds
CREATE TABLE rounds (
    round_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    description TEXT,
    is_knock_out BOOLEAN NOT NULL DEFAULT FALSE,
    updated_at TIMESTAMP
);

-- Matches
CREATE TABLE matches (
    match_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    round_id INT NOT NULL REFERENCES rounds(round_id),
    status_id INT NOT NULL REFERENCES status(status_id),
    description TEXT,
    team1_id INT REFERENCES teams(team_id),
    team2_id INT REFERENCES teams(team_id),
    winner_team_id INT REFERENCES teams(team_id),
    target1_id INT REFERENCES targets(target_id),
    target2_id INT REFERENCES targets(target_id),
    updated_at TIMESTAMP
);

-- Sets
CREATE TABLE sets (
    set_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    match_id INT NOT NULL REFERENCES matches(match_id),
    set_index SMALLINT NOT NULL,
    total_team1 INT,
    total_team2 INT,
    points_team1 INT,
    points_team2 INT,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP,
    CONSTRAINT unique_match_set_index UNIQUE (match_id, set_index)
);

-- Scores
CREATE TABLE scores (
    score_code VARCHAR(128) PRIMARY KEY,
    value INT NOT NULL,
    color CHAR(7) CHECK (color ~ '^#[0-9A-Fa-f]{6}$')
);

-- Arrows
CREATE TABLE arrows (
    arrow_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    score_code VARCHAR(128) NOT NULL REFERENCES scores(score_code),
    player_id INT NOT NULL REFERENCES players(player_id),
    set_id INT REFERENCES sets(set_id),
    arrow_index SMALLINT,
    CONSTRAINT unique_arrow_index_per_set_player UNIQUE (set_id, player_id, arrow_index)
);

-- Trigger Function
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Triggers
CREATE TRIGGER trg_rounds_updated_at
    BEFORE UPDATE ON rounds
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER trg_matches_updated_at
    BEFORE UPDATE ON matches
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER trg_sets_updated_at
    BEFORE UPDATE ON sets
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();
