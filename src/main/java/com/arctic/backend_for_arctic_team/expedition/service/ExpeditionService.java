package com.arctic.backend_for_arctic_team.expedition.service;


import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.expedition.exceptions.EditExpeditionException;
import com.arctic.backend_for_arctic_team.expedition.exceptions.ExpeditionNotFoundException;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.CreateExpeditionRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.EditExpeditionRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.ExpeditionResponse;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.UserExpeditionResponse;
import com.arctic.backend_for_arctic_team.expedition.model.entity.Expedition;
import com.arctic.backend_for_arctic_team.expedition.repository.ExpeditionRepository;
import com.arctic.backend_for_arctic_team.expedition.repository.ParticipantRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExpeditionService {
    private final ExpeditionRepository expeditionRepository;
    private final MapperService mapperService;
    private final ParticipantRepository participantRepository;

    public ExpeditionResponse createExpedition(CreateExpeditionRequest request, User currentUser) {

        Expedition expedition = mapperService.mapFromRequestToEntity(request, currentUser);
        Expedition savedExpedition = expeditionRepository.save(expedition);
        log.info("Expedition saved successfully");
        return ExpeditionResponse.mapFromEntityToResponse(savedExpedition);
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

    public ExpeditionResponse getExpeditionById(Long expeditionId){
        Expedition expedition = expeditionRepository.findById(expeditionId)
                .orElseThrow(() -> new ExpeditionNotFoundException("Данной экспедиции уже не существует"));

        return ExpeditionResponse.mapFromEntityToResponse(expedition);
    }

    public void deleteExpeditionById(Long id){
        expeditionRepository.deleteById(id);
    }

    public Expedition editExpedition(Long expeditionId, EditExpeditionRequest request) {
        Expedition expedition = expeditionRepository.findById(expeditionId)
                .orElseThrow(() -> new ExpeditionNotFoundException("Данной экспедиции уже не существует"));
        // изменение имени
        if (request.name() != null){
            expedition.setName(request.name());
        }
        // изменение описания
        if (request.description() != null){
            expedition.setDescription(request.description());
        }
        // проверка startDate < endDate, указанных в запросе
        if (request.endDate() != null && request.startDate() != null){
            if (request.endDate().isAfter(request.startDate())) {
                expedition.setStartDate(request.startDate());
                expedition.setEndDate(request.endDate());
            } else {
                throw new EditExpeditionException("Дата начала экспедиции должна быть позже даты окончания");
            }
        } else if (request.endDate() != null){ // проверка, что endDate в запросе больше, чем уже существующий startDate
            if (request.endDate().isAfter(expedition.getStartDate())) {
                expedition.setEndDate(request.endDate());
            } else {
                throw new EditExpeditionException("Дата начала экспедиции должна быть позже даты окончания");
            }
        } else if (request.startDate() != null){ // проверка, что startDate в запросе меньше, чем уже существующий endDate
            if (request.startDate().isBefore(expedition.getEndDate())) {
                expedition.setStartDate(request.startDate());
            } else {
                throw new EditExpeditionException("Дата начала экспедиции должна быть позже даты окончания");
            }
        }
        return expeditionRepository.save(expedition);
    }

    public boolean isLeaderOfExpedition(Long expeditionId, Long userId) {
        return expeditionRepository.findLeaderIdByExpeditionId(expeditionId)
                .map(leaderId -> leaderId.equals(userId))
                .orElse(false);
    }

    public boolean isParticipantOfExpedition(Long expeditionId, Long userId) {
        return participantRepository.existsByExpeditionIdAndUserId(expeditionId, userId);
    }
}
