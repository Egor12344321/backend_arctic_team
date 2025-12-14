package com.arctic.backend_for_arctic_team.metrics.repository;


import com.arctic.backend_for_arctic_team.metrics.model.entity.CardioMetrics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardioMetricsRepository extends JpaRepository<CardioMetrics, Long> {
}
