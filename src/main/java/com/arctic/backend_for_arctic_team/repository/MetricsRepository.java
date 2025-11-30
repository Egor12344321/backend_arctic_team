package com.arctic.backend_for_arctic_team.repository;

import com.arctic.backend_for_arctic_team.entity.Metrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricsRepository extends JpaRepository<Metrics, Long> {
}
