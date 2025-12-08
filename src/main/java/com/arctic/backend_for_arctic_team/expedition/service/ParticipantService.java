package com.arctic.backend_for_arctic_team.expedition.service;


import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.expedition.exceptions.ExpeditionNotFoundException;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.AddParticipantRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.ParticipantResponse;
import com.arctic.backend_for_arctic_team.expedition.model.entity.Participant;
import com.arctic.backend_for_arctic_team.expedition.repository.ExpeditionRepository;
import com.arctic.backend_for_arctic_team.expedition.repository.ParticipantRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final ExpeditionRepository expeditionRepository;

    @Transactional(readOnly = true)
    public List<ParticipantResponse> getExpeditionParticipants(Long expeditionId, User currentUser) {
        log.info("PARTICIPANT-SERVICE: Getting expedition participants");
        if (!expeditionRepository.existsById(expeditionId)) {
            log.warn("Expedition {} not found", expeditionId);
            throw new ExpeditionNotFoundException("Такой экспедиции не существует: " + expeditionId);
        }

        return participantRepository.findByExpeditionIdWithUser(expeditionId).stream().map(ParticipantResponse::mapFromEntityToResponse).toList();
    }

    public ParticipantResponse addParticipant(Long expeditionId, @Valid AddParticipantRequest request, User currentUser) {
    }

    public void removeParticipant(Long expeditionId, Long userId, User currentUser) {
    }
}
