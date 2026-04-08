package com.arctic.backend_for_arctic_team.expedition.controller;


import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.AddParticipantRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.CreateExpeditionRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.EditExpeditionRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.ExpeditionResponse;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.ParticipantResponse;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.UserExpeditionResponse;
import com.arctic.backend_for_arctic_team.expedition.model.entity.Expedition;
import com.arctic.backend_for_arctic_team.expedition.service.ExpeditionService;
import com.arctic.backend_for_arctic_team.expedition.service.ParticipantService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/expeditions")
public class ExpeditionController {
    private final ExpeditionService expeditionService;
    private final ParticipantService participantService;

    @PostMapping
    @PreAuthorize("hasRole('LEADER')")
    @Operation(summary = "Создание экспедиции")
    public ResponseEntity<ExpeditionResponse> createExpedition(
            @Valid @RequestBody CreateExpeditionRequest request,
            @AuthenticationPrincipal User currentUser) {

        log.debug("EXPEDITION-CONTROLLER: Leader {} creating expedition: {}", currentUser.getId(), request.name());
        ExpeditionResponse expedition = expeditionService.createExpedition(request, currentUser);
        log.debug("EXPEDITION-CONTROLLER: Leader {} creating expedition: {} ENDED", currentUser.getId(), request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(expedition);
    }

    @GetMapping("/{expeditionId}")
    @PreAuthorize("hasRole('LEADER') and @expeditionSecurity.isLeaderOfExpedition(authentication, #expeditionId) or hasRole('USER') and @expeditionSecurity.isParticipantOfExpedition(authentication, #expeditionId) or hasRole('ADMIN')")
    @Operation(summary = "Получение данных по экспедиции")
    public ResponseEntity<ExpeditionResponse> getExpedition(@PathVariable Long expeditionId){
        log.debug("EXPEDITION-CONTROLLER: Получение данных по экспедиции expeditionId: {}", expeditionId);
        ExpeditionResponse expeditionResponse = expeditionService.getExpeditionById(expeditionId);
        return ResponseEntity.ok().body(expeditionResponse);
    }


    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('USER', 'LEADER', 'ADMIN')")
    @Operation(summary = "Получить собственные экспедиции")
    public ResponseEntity<UserExpeditionResponse> getMyExpeditions(
            @AuthenticationPrincipal User currentUser) {

        log.debug("User {} requesting their expeditions", currentUser.getId());

        UserExpeditionResponse expeditions = expeditionService.getUserExpeditions(currentUser);
        return ResponseEntity.ok(expeditions);
    }

    @DeleteMapping("/{expeditionId}")
    @PreAuthorize("hasRole('LEADER') and @expeditionSecurity.isLeaderOfExpedition(authentication, #expeditionId)")
    @Operation(summary = "Удалить экспедицию по id")
    public ResponseEntity<Void> deleteExpedition(
            @PathVariable Long expeditionId,
            @AuthenticationPrincipal User currentUser) {

        log.debug("Leader {} deleting expedition {} STARTED", currentUser.getId(), expeditionId);
        expeditionService.deleteExpeditionById(expeditionId);
        log.debug("Leader {} deleting expedition {} ENDED", currentUser.getId(), expeditionId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('LEADER') and @expeditionSecurity.isLeaderOfExpedition(authentication, #expeditionId) or hasRole('ADMIN')")
    @GetMapping("/{expeditionId}/participants")
    @Operation(summary = "Получить участников экспедиции по id")
    public ResponseEntity<List<ParticipantResponse>> getExpeditionParticipants(
            @PathVariable Long expeditionId,
            @AuthenticationPrincipal User currentUser) {

        log.debug("Leader {} VIEWING participants of expedition {}",
                currentUser.getId(), expeditionId);
        List<ParticipantResponse> participants = participantService.getExpeditionParticipants(
                expeditionId);
        return ResponseEntity.ok(participants);
    }

    @PostMapping("/{expeditionId}/participants")
    @PreAuthorize("hasRole('LEADER') and @expeditionSecurity.isLeaderOfExpedition(authentication, #expeditionId) or hasRole('ADMIN')")
    @Operation(summary = "Добавить нового участника экспедиции по индивидуальному номеру")
    public ResponseEntity<ParticipantResponse> addParticipant(
            @PathVariable Long expeditionId,
            @Valid @RequestBody AddParticipantRequest request,
            @AuthenticationPrincipal User currentUser) {

        log.debug("Leader {} ADDING participant {} to expedition {} STARTED",
                currentUser.getId(), request.individualNumber(), expeditionId);
        ParticipantResponse participant = participantService.addParticipant(
                expeditionId, request);
        log.debug("Leader {} ADDING participant {} to expedition {} ENDED",
                currentUser.getId(), request.individualNumber(), expeditionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(participant);
    }

    @DeleteMapping("/{expeditionId}/participants/{participantId}")
    @PreAuthorize("hasRole('LEADER') and @expeditionSecurity.isLeaderOfExpedition(authentication, #expeditionId)")
    @Operation(summary = "Удалить участника экспедиции")
    public ResponseEntity<Void> removeParticipant(
            @PathVariable Long expeditionId,
            @PathVariable Long participantId,
            @AuthenticationPrincipal User currentUser) {

        log.debug("Leader {} removing participant {} from expedition {} STARTED",
                currentUser.getId(), participantId, expeditionId);
        participantService.removeParticipant(expeditionId, participantId, currentUser);
        log.debug("Leader {} removing participant {} from expedition {} ENDED",
                currentUser.getId(), participantId, expeditionId);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{expeditionId}")
    @PreAuthorize("hasRole('LEADER') and @expeditionSecurity.isLeaderOfExpedition(authentication, #expeditionId) or hasRole('ADMIN')")
    @Operation(summary = "Редактирование экспедиции")
    public ResponseEntity<?> editExpedition(@PathVariable Long expeditionId, @AuthenticationPrincipal User currentUser,
                                            @RequestBody @Valid EditExpeditionRequest request){
        log.debug("EXPEDITION-CONTROLLER: Started editing expedition");
        Expedition expedition = expeditionService.editExpedition(expeditionId, request);
        log.debug("EXPEDITION-CONTROLLER: Ended editing expedition");
        return ResponseEntity.ok(ExpeditionResponse.mapFromEntityToResponse(expedition));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Получение всех экспедиций")
    public ResponseEntity<Page<ExpeditionResponse>> getAllExpeditions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @AuthenticationPrincipal User user
    ){
        log.debug("EXPEDITION-CONTROLLER: Started getting expeditions by admin with pagination: page={}, size={}", page, size);
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<ExpeditionResponse> expeditions = expeditionService.getAllExpeditionsPaginated(pageable);

        log.debug("EXPEDITION-CONTROLLER: Ended getting expeditions by admin");
        return ResponseEntity.ok(expeditions);

    }
}
