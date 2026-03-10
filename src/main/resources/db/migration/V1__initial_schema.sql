CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    individual_number VARCHAR(255) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enabled BOOLEAN DEFAULT TRUE,
    account_non_expired BOOLEAN DEFAULT TRUE,
    account_non_locked BOOLEAN DEFAULT TRUE,
    credentials_non_expired BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, role)
);

CREATE TABLE IF NOT EXISTS expeditions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    leader_id BIGINT NOT NULL REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS participants (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    expedition_id BIGINT NOT NULL REFERENCES expeditions(id) ON DELETE CASCADE,
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, expedition_id)
);

CREATE TABLE IF NOT EXISTS cardio_metrics (
    id BIGSERIAL PRIMARY KEY,
    individual_number VARCHAR(255) NOT NULL,
    expedition_id BIGSERIAL,
    timestamp BIGINT NOT NULL,
    session INTEGER,
    heart_rate DOUBLE PRECISION,
    has_artifacts INTEGER,
    kaplan_index DOUBLE PRECISION,
    metrics_available INTEGER,
    motion_artifacts INTEGER,
    skin_contact INTEGER,
    stress_index DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS eeg_artifacts_metrics (
    id BIGSERIAL PRIMARY KEY,
    expedition_id BIGSERIAL,
    artifacts_channel_1 INTEGER,
    artifacts_channel_2 INTEGER,
    quality_channel_1 INTEGER,
    quality_channel_2 INTEGER,
    individual_number VARCHAR(255) NOT NULL,
    timestamp BIGINT NOT NULL,
    session INTEGER
);

CREATE TABLE IF NOT EXISTS eeg_proceed_metrics (
    id BIGSERIAL PRIMARY KEY,
    expedition_id BIGSERIAL,
    channel_1 REAL,
    channel_2 REAL,
    individual_number VARCHAR(255) NOT NULL,
    timestamp BIGINT NOT NULL,
    session INTEGER
);

CREATE TABLE IF NOT EXISTS eeg_raw_metrics (
    id BIGSERIAL PRIMARY KEY,
    expedition_id BIGSERIAL,
    channel_1 REAL,
    channel_2 REAL,
    individual_number VARCHAR(255) NOT NULL,
    timestamp BIGINT NOT NULL,
    session INTEGER
);

CREATE TABLE IF NOT EXISTS emotional_metrics (
    id BIGSERIAL PRIMARY KEY,
    expedition_id BIGSERIAL,
    individual_number VARCHAR(255) NOT NULL,
    timestamp BIGINT NOT NULL,
    session INTEGER,
    attention DOUBLE PRECISION,
    relaxation DOUBLE PRECISION,
    cognitive_load DOUBLE PRECISION,
    cognitive_control DOUBLE PRECISION,
    self_control DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS mems_metrics (
    id BIGSERIAL PRIMARY KEY,
    expedition_id BIGSERIAL,
    individual_number VARCHAR(255) NOT NULL,
    timestamp BIGINT NOT NULL,
    session INTEGER,
    accelerometer_x DOUBLE PRECISION,
    accelerometer_y DOUBLE PRECISION,
    accelerometer_z DOUBLE PRECISION,
    gyroscope_x DOUBLE PRECISION,
    gyroscope_y DOUBLE PRECISION,
    gyroscope_z DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS nfb_metrics (
    id BIGSERIAL PRIMARY KEY,
    expedition_id BIGSERIAL,
    individual_number VARCHAR(255) NOT NULL,
    timestamp BIGINT NOT NULL,
    session INTEGER,
    alpha DOUBLE PRECISION,
    beta DOUBLE PRECISION,
    theta DOUBLE PRECISION,
    delta DOUBLE PRECISION,
    smr DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS physiological_metrics (
    id BIGSERIAL PRIMARY KEY,
    expedition_id BIGSERIAL,
    individual_number VARCHAR(255) NOT NULL,
    timestamp BIGINT NOT NULL,
    session INTEGER,
    relax DOUBLE PRECISION,
    fatigue DOUBLE PRECISION,
    none DOUBLE PRECISION,
    concentration DOUBLE PRECISION,
    involvement DOUBLE PRECISION,
    stress DOUBLE PRECISION,
    nfb_artifacts INTEGER,
    cardio_artifacts INTEGER
);

CREATE TABLE IF NOT EXISTS productivity_metrics (
    id BIGSERIAL PRIMARY KEY,
    expedition_id BIGSERIAL,
    individual_number VARCHAR(255) NOT NULL,
    timestamp BIGINT NOT NULL,
    session INTEGER,
    gravity DOUBLE PRECISION,
    productivity DOUBLE PRECISION,
    fatigue DOUBLE PRECISION,
    reverse_fatigue DOUBLE PRECISION,
    relaxation DOUBLE PRECISION,
    concentration DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS cardio_metrics_compressed (
    id BIGSERIAL PRIMARY KEY,
    individual_number VARCHAR(255) NOT NULL,
    expedition_id VARCHAR(255),
    timestamp BIGINT NOT NULL,
    session INTEGER,
    heart_rate DOUBLE PRECISION,
    has_artifacts INTEGER,
    kaplan_index DOUBLE PRECISION,
    metrics_available INTEGER,
    motion_artifacts INTEGER,
    skin_contact INTEGER,
    stress_index DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS eeg_artifacts_metrics_compressed (
    id BIGSERIAL PRIMARY KEY,
    expedition_id VARCHAR(255),
    artifacts_channel_1 INTEGER,
    artifacts_channel_2 INTEGER,
    quality_channel_1 INTEGER,
    quality_channel_2 INTEGER,
    individual_number VARCHAR(255) NOT NULL,
    timestamp BIGINT NOT NULL,
    session INTEGER
);

CREATE TABLE IF NOT EXISTS eeg_proceed_metrics_compressed (
    id BIGSERIAL PRIMARY KEY,
    expedition_id VARCHAR(255),
    channel_1 REAL,
    channel_2 REAL,
    individual_number VARCHAR(255) NOT NULL,
    timestamp BIGINT NOT NULL,
    session INTEGER
);

CREATE TABLE IF NOT EXISTS eeg_raw_metrics_compressed (
    id BIGSERIAL PRIMARY KEY,
    expedition_id VARCHAR(255),
    channel_1 REAL,
    channel_2 REAL,
    individual_number VARCHAR(255) NOT NULL,
    timestamp BIGINT NOT NULL,
    session INTEGER
);

CREATE TABLE IF NOT EXISTS emotional_metrics_compressed (
    id BIGSERIAL PRIMARY KEY,
    expedition_id VARCHAR(255),
    individual_number VARCHAR(255) NOT NULL,
    timestamp BIGINT NOT NULL,
    session INTEGER,
    attention DOUBLE PRECISION,
    relaxation DOUBLE PRECISION,
    cognitive_load DOUBLE PRECISION,
    cognitive_control DOUBLE PRECISION,
    self_control DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS mems_metrics_compressed (
    id BIGSERIAL PRIMARY KEY,
    expedition_id VARCHAR(255),
    individual_number VARCHAR(255) NOT NULL,
    timestamp BIGINT NOT NULL,
    session INTEGER,
    accelerometer_x DOUBLE PRECISION,
    accelerometer_y DOUBLE PRECISION,
    accelerometer_z DOUBLE PRECISION,
    gyroscope_x DOUBLE PRECISION,
    gyroscope_y DOUBLE PRECISION,
    gyroscope_z DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS nfb_metrics_compressed (
    id BIGSERIAL PRIMARY KEY,
    expedition_id VARCHAR(255),
    individual_number VARCHAR(255) NOT NULL,
    timestamp BIGINT NOT NULL,
    session INTEGER,
    alpha DOUBLE PRECISION,
    beta DOUBLE PRECISION,
    theta DOUBLE PRECISION,
    delta DOUBLE PRECISION,
    smr DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS physiological_metrics_compressed (
    id BIGSERIAL PRIMARY KEY,
    expedition_id VARCHAR(255),
    individual_number VARCHAR(255) NOT NULL,
    timestamp BIGINT NOT NULL,
    session INTEGER,
    relax DOUBLE PRECISION,
    fatigue DOUBLE PRECISION,
    none DOUBLE PRECISION,
    concentration DOUBLE PRECISION,
    involvement DOUBLE PRECISION,
    stress DOUBLE PRECISION,
    nfb_artifacts INTEGER,
    cardio_artifacts INTEGER
);

CREATE TABLE IF NOT EXISTS productivity_metrics_compressed (
    id BIGSERIAL PRIMARY KEY,
    expedition_id VARCHAR(255),
    individual_number VARCHAR(255) NOT NULL,
    timestamp BIGINT NOT NULL,
    session INTEGER,
    gravity DOUBLE PRECISION,
    productivity DOUBLE PRECISION,
    fatigue DOUBLE PRECISION,
    reverse_fatigue DOUBLE PRECISION,
    relaxation DOUBLE PRECISION,
    concentration DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS physiological_baseline (
    id BIGSERIAL PRIMARY KEY,
    individual_number VARCHAR(255),
    timestamp BIGINT NOT NULL,
    expedition_id VARCHAR(255),
    session INTEGER,
    alpha DOUBLE PRECISION,
    beta DOUBLE PRECISION,
    alpha_gravity DOUBLE PRECISION,
    beta_gravity DOUBLE PRECISION,
    concentration DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS productivity_baseline (
    id BIGSERIAL PRIMARY KEY,
    expedition_id VARCHAR(255),
    individual_number VARCHAR(255),
    timestamp BIGINT NOT NULL,
    session INTEGER,
    gravity DOUBLE PRECISION,
    productivity DOUBLE PRECISION,
    fatigue DOUBLE PRECISION,
    reverse_fatigue DOUBLE PRECISION,
    relaxation DOUBLE PRECISION,
    concentration DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS productivity_index (
   id BIGSERIAL PRIMARY KEY,
   expedition_id VARCHAR(255),
   individual_number VARCHAR(255),
   timestamp BIGINT NOT NULL,
   session INTEGER,
   relaxation VARCHAR(255),
   stress VARCHAR(255),
   gravity_baseline DOUBLE PRECISION,
   productivity_baseline DOUBLE PRECISION,
   fatigue_baseline DOUBLE PRECISION,
   reverse_fatigue_baseline DOUBLE PRECISION,
   relaxation_baseline DOUBLE PRECISION,
   concentration_baseline DOUBLE PRECISION,
   has_artifacts BOOLEAN
);