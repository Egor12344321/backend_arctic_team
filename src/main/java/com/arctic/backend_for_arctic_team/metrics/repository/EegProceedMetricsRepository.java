package com.arctic.backend_for_arctic_team.metrics.repository;

import com.arctic.backend_for_arctic_team.metrics.model.entity.EEGProceedMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EegProceedMetricsRepository extends JpaRepository<EEGProceedMetrics, Long> {
}
