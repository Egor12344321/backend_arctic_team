package com.arctic.backend_for_arctic_team.metrics.repository.jpa;

import com.arctic.backend_for_arctic_team.metrics.model.entity.PhysiologicalMetricsCompressed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhysiologicalMetricsCompressedRepository extends JpaRepository<PhysiologicalMetricsCompressed, Long> {
}
