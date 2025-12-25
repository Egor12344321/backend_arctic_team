package com.arctic.backend_for_arctic_team.metrics.repository;

import com.arctic.backend_for_arctic_team.metrics.model.entity.EEGRawMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EegRawMetricsRepository extends JpaRepository<EEGRawMetrics, Long> {
}
