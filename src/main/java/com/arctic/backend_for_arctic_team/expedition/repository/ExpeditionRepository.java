package com.arctic.backend_for_arctic_team.expedition.repository;

import com.arctic.backend_for_arctic_team.expedition.model.entity.Expedition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ExpeditionRepository extends JpaRepository<Expedition, Long> {

    List<Expedition> findByLeaderId(Long id);

    @Query("SELECT e.leader.id FROM Expedition e WHERE e.id = :expeditionId")
    Optional<Long> findLeaderIdByExpeditionId(@Param("expeditionId") Long expeditionId);
}
