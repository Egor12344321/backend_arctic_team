package com.arctic.backend_for_arctic_team.metrics.repository;


import com.arctic.backend_for_arctic_team.metrics.model.entity.CardioMetrics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardioMetricsRepository extends JpaRepository<CardioMetrics, Long> {
    List<CardioMetrics> findByIndividualNumberAndTimestampBetweenOrderByTimestamp(String individualNumber,
                                                                                  Long startTimestamp,
                                                                                  Long endTimestamp);
}
