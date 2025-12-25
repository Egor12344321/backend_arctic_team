package com.arctic.backend_for_arctic_team.metrics.repository;

import com.arctic.backend_for_arctic_team.metrics.model.entity.EEGArtifactsMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EegArtifactsMetricsRepository extends JpaRepository<EEGArtifactsMetrics, Long> {
}
