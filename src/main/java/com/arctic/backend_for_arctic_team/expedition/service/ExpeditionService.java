package com.arctic.backend_for_arctic_team.expedition.service;


import com.arctic.backend_for_arctic_team.auth.custom_exceptions.UserNotFoundException;
import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.auth.repository.UserRepository;
import com.arctic.backend_for_arctic_team.auth.security.UserDetailsImpl;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.CreateExpeditionRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.UpdateExpeditionRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.ExpeditionResponse;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.UserExpeditionResponse;
import com.arctic.backend_for_arctic_team.expedition.model.entity.Expedition;
import com.arctic.backend_for_arctic_team.expedition.model.entity.Participant;
import com.arctic.backend_for_arctic_team.expedition.repository.ExpeditionRepository;
import com.arctic.backend_for_arctic_team.expedition.repository.ParticipantRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExpeditionService {
    private final ExpeditionRepository expeditionRepository;
    private final MapperService mapperService;
    private final ParticipantRepository participantRepository;

    public ExpeditionResponse createExpedition(@Valid CreateExpeditionRequest request, User currentUser) {
        Expedition expedition = mapperService.mapFromRequestToEntity(request, currentUser);
        expeditionRepository.save(expedition);
        log.info("Expedition saved successfully");
        return ExpeditionResponse.mapFromEntityToResponse(expedition);
    }

    public UserExpeditionResponse getUserExpeditions(User currentUser) {
        List<ExpeditionResponse> leaderExpeditions = expeditionRepository.findByLeaderId(currentUser.getId())
                .stream()
                .map(ExpeditionResponse::forLeader)
                .toList();
        log.debug("LEADER-EXPEDITIONS: {}", leaderExpeditions);
        List<ExpeditionResponse> partExpeditions = participantRepository.findParticipantExpeditionsExcludingLeader(currentUser.getId())
                .stream()
                .map(ExpeditionResponse::forParticipant)
                .toList();
        log.debug("PARTICIPANT-EXPEDITIONS: {}", partExpeditions);
        return new UserExpeditionResponse(
                partExpeditions,
                leaderExpeditions
        );
    }

    public String deleteExpeditionById(Long id){
        expeditionRepository.deleteById(id);
        return "Экспедиция удалена";
    }
}
