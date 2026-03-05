package com.arctic.backend_for_arctic_team.metrics.repository;

import com.arctic.backend_for_arctic_team.metrics.model.entity.PhysiologicalBaseline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhysiologicalBaselineRepository extends JpaRepository<PhysiologicalBaseline, Long> {
}
