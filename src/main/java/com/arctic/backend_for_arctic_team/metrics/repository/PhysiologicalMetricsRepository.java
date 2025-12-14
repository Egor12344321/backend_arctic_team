package com.arctic.backend_for_arctic_team.metrics.repository;

import com.arctic.backend_for_arctic_team.metrics.model.entity.PhysiologicalMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhysiologicalMetricsRepository extends JpaRepository<PhysiologicalMetrics, Long> {
}
