CREATE TABLE IF NOT EXISTS session_results (
    id BIGSERIAL PRIMARY KEY,
    individual_number VARCHAR(255) NOT NULL,
    expedition_id BIGINT NOT NULL,
    session BIGINT NOT NULL,

    objective_cognitive INTEGER,
    objective_psychological INTEGER,
    objective_physiological INTEGER,

    subjective_cognitive INTEGER,
    subjective_psychological INTEGER,
    subjective_physiological INTEGER,

    total_index INTEGER,
    average_objective INTEGER,
    average_subjective INTEGER,
    total_cognitive INTEGER,
    total_physiological INTEGER,
    total_psychological INTEGER,

    duration_minutes INTEGER,
    end_time INTEGER,
    session_category VARCHAR(50),
    comment TEXT,

    objective_fatigue VARCHAR(50),
    objective_stress VARCHAR(50),
    passing_prematurely BOOLEAN,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_session_results_individual ON session_results(individual_number);
CREATE INDEX idx_session_results_expedition ON session_results(expedition_id);
CREATE INDEX idx_session_results_individual_expedition_session ON session_results(individual_number, expedition_id, session);