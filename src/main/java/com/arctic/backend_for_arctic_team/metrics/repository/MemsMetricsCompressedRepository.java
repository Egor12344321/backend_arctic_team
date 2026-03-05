package com.arctic.backend_for_arctic_team.metrics.repository;

import com.arctic.backend_for_arctic_team.metrics.model.entity.MemsMetrics;
import com.arctic.backend_for_arctic_team.metrics.model.entity.MemsMetricsCompressed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemsMetricsCompressedRepository extends JpaRepository<MemsMetricsCompressed, Long> {
}
