package com.arctic.backend_for_arctic_team.metrics.repository;

import com.arctic.backend_for_arctic_team.metrics.model.entity.EmotionalMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionalMetricsRepository extends JpaRepository<EmotionalMetrics, Long> {
}
