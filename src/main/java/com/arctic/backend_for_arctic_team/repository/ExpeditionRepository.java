package com.arctic.backend_for_arctic_team.repository;

import com.arctic.backend_for_arctic_team.entity.Expedition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ExpeditionRepository extends JpaRepository<Expedition, Long> {
}
