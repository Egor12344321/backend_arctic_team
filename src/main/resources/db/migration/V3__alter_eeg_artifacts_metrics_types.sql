ALTER TABLE eeg_artifacts_metrics
ALTER COLUMN artifacts_channel_1 TYPE BOOLEAN
USING (artifacts_channel_1::BOOLEAN);

ALTER TABLE eeg_artifacts_metrics
ALTER COLUMN artifacts_channel_2 TYPE BOOLEAN
USING (artifacts_channel_2::BOOLEAN);

ALTER TABLE eeg_artifacts_metrics
ALTER COLUMN quality_channel_1 TYPE REAL
USING (quality_channel_1::REAL / 100.0);

ALTER TABLE eeg_artifacts_metrics
ALTER COLUMN quality_channel_2 TYPE REAL
USING (quality_channel_2::REAL / 100.0);

ALTER TABLE eeg_artifacts_metrics_compressed
ALTER COLUMN artifacts_channel_1 TYPE BOOLEAN
USING (artifacts_channel_1::BOOLEAN);

ALTER TABLE eeg_artifacts_metrics_compressed
ALTER COLUMN artifacts_channel_2 TYPE BOOLEAN
USING (artifacts_channel_2::BOOLEAN);

ALTER TABLE eeg_artifacts_metrics_compressed
ALTER COLUMN quality_channel_1 TYPE REAL
USING (quality_channel_1::REAL / 100.0);

ALTER TABLE eeg_artifacts_metrics_compressed
ALTER COLUMN quality_channel_2 TYPE REAL
USING (quality_channel_2::REAL / 100.0);