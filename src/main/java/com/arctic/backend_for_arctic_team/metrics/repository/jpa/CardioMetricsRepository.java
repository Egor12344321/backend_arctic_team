package com.arctic.backend_for_arctic_team.metrics.repository.jpa;


import com.arctic.backend_for_arctic_team.metrics.model.entity.CardioMetrics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardioMetricsRepository extends JpaRepository<CardioMetrics, Long> {
    List<CardioMetrics> findAllByIndividualNumberAndExpeditionId(String individualNumber, Long expeditionId);
}
