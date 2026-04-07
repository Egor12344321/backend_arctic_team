package com.arctic.backend_for_arctic_team.metrics.repository.jpa;

import com.arctic.backend_for_arctic_team.metrics.model.entity.SessionResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionResultsRepository extends JpaRepository<SessionResults, Long> {

    List<SessionResults> findByIndividualNumberAndExpeditionIdOrderBySessionAsc(String individualNumber, Long expeditionId);

    List<SessionResults> findTop5ByIndividualNumberAndExpeditionIdOrderBySessionDesc(String individualNumber, Long expeditionId);
}