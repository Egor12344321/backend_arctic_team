package com.arctic.backend_for_arctic_team.expedition.repository;

import com.arctic.backend_for_arctic_team.expedition.model.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
