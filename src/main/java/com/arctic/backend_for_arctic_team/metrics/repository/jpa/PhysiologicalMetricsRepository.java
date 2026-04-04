package com.arctic.backend_for_arctic_team.metrics.repository.jpa;

import com.arctic.backend_for_arctic_team.metrics.model.entity.PhysiologicalMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhysiologicalMetricsRepository extends JpaRepository<PhysiologicalMetrics, Long> {
    List<PhysiologicalMetrics> findAllByIndividualNumberAndExpeditionId(String individualNumber, Long expeditionId);
}
