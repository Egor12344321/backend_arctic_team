package com.arctic.backend_for_arctic_team.expedition.service;


import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.auth.repository.UserRepository;
import com.arctic.backend_for_arctic_team.expedition.exceptions.ExpeditionNotFoundException;
import com.arctic.backend_for_arctic_team.expedition.exceptions.ParticipantException;
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
import org.springframework.security.access.AccessDeniedException;
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
        log.debug("PARTICIPANT-SERVICE: Getting expedition participants");
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
        log.debug("PARTICIPANT-SERVICE: Starting adding participant: {}", request.individualNumber());

        Expedition expedition = expeditionRepository.findById(expeditionId)
                .orElseThrow(() -> new ExpeditionNotFoundException("Экспедиция с таким id: " + expeditionId + " не найдена"));

        log.debug("PARTICIPANT-SERVICE: Expedition fount");
        User user = userRepository.findByIndividualNumber(request.individualNumber())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с таким индивидуальным номером не найден"));
        if (checkUserInExpedition(expeditionId, user.getId())){
            throw new ParticipantException(ParticipantException.ParticipantError.ALREADY_EXISTS, "Пользователь " + user.getId() + "уже участник данной экспедиции " + expeditionId);
        }


        log.debug("PARTICIPANT-SERVICE: User fount");
        Participant participant = Participant.builder()
                .user(user)
                .expedition(expedition)
                .build();
        participantRepository.save(participant);
        log.info("Добавлен новый участник: {} (id={}) в экспедицию: {} (id={})", participant.getUser().getEmail(), participant.getUser().getId(), participant.getExpedition().getName(), participant.getExpedition().getId());
        return ParticipantResponse.mapFromEntityToResponse(participant);

    }

    private boolean checkUserInExpedition(Long expeditionId, Long id){
        return participantRepository.existsByExpeditionIdAndUserId(expeditionId, id);
    }

    public void removeParticipant(Long expeditionId, Long participantId, User user) {
        log.info("Пользователь c id участника: {} удален из экспедиции: {} лидером: {}", participantId, expeditionId, user.getId());
        participantRepository.deleteById(participantId); // исправить на мягкое удаление
    }


}
