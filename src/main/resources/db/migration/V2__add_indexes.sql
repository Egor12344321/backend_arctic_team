CREATE INDEX IF NOT EXISTS idx_cardio_indnum_expid ON cardio_metrics (individual_number, expedition_id);
CREATE INDEX IF NOT EXISTS idx_nfb_indnum_expid ON nfb_metrics (individual_number, expedition_id);
CREATE INDEX IF NOT EXISTS idx_physio_indnum_expid ON physiological_metrics (individual_number, expedition_id);
CREATE INDEX IF NOT EXISTS idx_productivity_indnum_expid ON productivity_baseline (individual_number, expedition_id);