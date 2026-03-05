package com.arctic.backend_for_arctic_team.metrics.repository;

import com.arctic.backend_for_arctic_team.metrics.model.entity.ProductivityIndex;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductivityIndexRepository extends JpaRepository<ProductivityIndex, Long> {
}
