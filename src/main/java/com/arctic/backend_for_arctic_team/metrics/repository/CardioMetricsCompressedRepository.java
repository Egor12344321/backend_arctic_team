package com.arctic.backend_for_arctic_team.metrics.repository;


import com.arctic.backend_for_arctic_team.metrics.model.entity.CardioMetrics;
import com.arctic.backend_for_arctic_team.metrics.model.entity.CardioMetricsCompressed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardioMetricsCompressedRepository extends JpaRepository<CardioMetricsCompressed, Long> {
    List<CardioMetricsCompressed> findByIndividualNumberAndTimestampBetweenOrderByTimestamp(String individualNumber,
                                                                                  Long startTimestamp,
                                                                                  Long endTimestamp);
}
