package com.arctic.backend_for_arctic_team.metrics.repository.jpa;

import com.arctic.backend_for_arctic_team.metrics.model.entity.NfbMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NfbMetricsRepository extends JpaRepository<NfbMetrics, Long> {
    List<NfbMetrics> findAllByIndividualNumberAndExpeditionId(String individualNumber, Long expeditionId);
}
