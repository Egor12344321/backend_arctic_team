package com.arctic.backend_for_arctic_team.expedition.repository;

import com.arctic.backend_for_arctic_team.expedition.model.entity.Expedition;
import com.arctic.backend_for_arctic_team.expedition.model.entity.Participant;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    public List<Participant> findByUserId(Long id);

    @Query("SELECT p.expedition FROM Participant p " +
            "WHERE p.user.id = :userId " +
            "AND p.expedition.leader.id != :userId")
    List<Expedition> findParticipantExpeditionsExcludingLeader(@Param("userId") Long userId);

    @Query("SELECT p FROM Participant p " +
            "JOIN FETCH p.user u " +
            "WHERE p.expedition.id = :expeditionId " +
            "ORDER BY u.lastName, u.firstName")
    List<Participant> findByExpeditionIdWithUser(@Param("expeditionId") Long expeditionId);

    boolean existsByUserIdAndExpeditionId(Long userId, Long expeditionId);
}
