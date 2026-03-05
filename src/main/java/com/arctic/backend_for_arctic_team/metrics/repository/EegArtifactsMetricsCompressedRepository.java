package com.arctic.backend_for_arctic_team.metrics.repository;

import com.arctic.backend_for_arctic_team.metrics.model.entity.EEGArtifactsMetrics;
import com.arctic.backend_for_arctic_team.metrics.model.entity.EEGArtifactsMetricsCompressed;
import com.arctic.backend_for_arctic_team.metrics.model.entity.EEGProceedMetricsCompressed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EegArtifactsMetricsCompressedRepository extends JpaRepository<EEGArtifactsMetricsCompressed, Long> {
}
