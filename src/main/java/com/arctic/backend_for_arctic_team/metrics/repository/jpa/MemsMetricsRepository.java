package com.arctic.backend_for_arctic_team.metrics.repository.jpa;

import com.arctic.backend_for_arctic_team.metrics.model.entity.MemsMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemsMetricsRepository extends JpaRepository<MemsMetrics, Long> {
}
