package com.arctic.backend_for_arctic_team.metrics.repository;

import com.arctic.backend_for_arctic_team.metrics.model.entity.NfbMetrics;
import com.arctic.backend_for_arctic_team.metrics.model.entity.ProductivityMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductivityMetricsRepository extends JpaRepository<ProductivityMetrics, Long> {
    List<ProductivityMetrics> findAllByIndividualNumberAndExpeditionId(String individualNumber, Long expeditionId);
}
