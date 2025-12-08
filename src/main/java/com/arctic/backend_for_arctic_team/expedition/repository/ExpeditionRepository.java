package com.arctic.backend_for_arctic_team.expedition.repository;

import com.arctic.backend_for_arctic_team.expedition.model.entity.Expedition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExpeditionRepository extends JpaRepository<Expedition, Long> {
    List<Expedition> findByUserId(Long id);

    List<Expedition> findByLeaderId(Long id);
}
