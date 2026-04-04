package com.arctic.backend_for_arctic_team.metrics.repository.jpa;

import com.arctic.backend_for_arctic_team.metrics.model.entity.ProductivityMetricsCompressed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductivityMetricsCompressedRepository extends JpaRepository<ProductivityMetricsCompressed, Long> {
}
