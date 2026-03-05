package com.arctic.backend_for_arctic_team.metrics.repository;

import com.arctic.backend_for_arctic_team.metrics.model.entity.EEGRawMetrics;
import com.arctic.backend_for_arctic_team.metrics.model.entity.EEGRawMetricsCompressed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EegRawMetricsCompressedRepository extends JpaRepository<EEGRawMetricsCompressed, Long> {
}
