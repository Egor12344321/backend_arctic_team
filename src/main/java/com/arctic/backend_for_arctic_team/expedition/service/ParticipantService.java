package com.arctic.backend_for_arctic_team.expedition.service;


import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.auth.repository.UserRepository;
import com.arctic.backend_for_arctic_team.expedition.exceptions.ExpeditionNotFoundException;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.AddParticipantRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.ParticipantResponse;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.UserResponse;
import com.arctic.backend_for_arctic_team.expedition.model.entity.Expedition;
import com.arctic.backend_for_arctic_team.expedition.model.entity.Participant;
import com.arctic.backend_for_arctic_team.expedition.repository.ExpeditionRepository;
import com.arctic.backend_for_arctic_team.expedition.repository.ParticipantRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final ExpeditionRepository expeditionRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ParticipantResponse> getExpeditionParticipants(Long expeditionId) {
        log.info("PARTICIPANT-SERVICE: Getting expedition participants");
        if (!expeditionRepository.existsById(expeditionId)) {
            log.warn("Expedition {} not found", expeditionId);
            throw new ExpeditionNotFoundException("Такой экспедиции не существует: " + expeditionId);
        }

        return participantRepository.findByExpeditionIdWithUser(expeditionId)
                .stream()
                .map(ParticipantResponse::mapFromEntityToResponse)
                .toList();
    }

    public ParticipantResponse addParticipant(Long expeditionId, AddParticipantRequest request) {
        log.info("PARTICIPANT-SERVICE: Starting adding participant: {}", request.individualNumber());

        Expedition expedition = expeditionRepository.findById(expeditionId)
                .orElseThrow(() -> new ExpeditionNotFoundException("Экспедиция с таким id: " + expeditionId + " не найдена"));
        log.info("PARTICIPANT-SERVICE: Expedition fount");
        User user = userRepository.findByIndividualNumber(request.individualNumber())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с таким индивидуальным номером не найден"));
        log.info("PARTICIPANT-SERVICE: User fount");
        Participant participant = Participant.builder()
                .user(user)
                .expedition(expedition)
                .build();
        participantRepository.save(participant);
        log.info("PARTICIPANT-SERVICE: Participant saved");
        return ParticipantResponse.mapFromEntityToResponse(participant);

    }

    public void removeParticipant(Long expeditionId, Long participantId) {
        log.info("PARTICIPANT-SERVICE: Deleting participant started");
        participantRepository.deleteById(participantId);
    }


}
