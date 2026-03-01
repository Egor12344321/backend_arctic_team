package com.arctic.backend_for_arctic_team.expedition.controller;


import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.AddParticipantRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.CreateExpeditionRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.EditExpeditionRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.ExpeditionResponse;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.ParticipantResponse;
import com.arctic.backend_for_arctic_team.expedition.model.entity.Expedition;
import com.arctic.backend_for_arctic_team.expedition.service.ChartsService;
import com.arctic.backend_for_arctic_team.expedition.service.ExpeditionService;
import com.arctic.backend_for_arctic_team.expedition.service.ParticipantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/expeditions/leader")
@Validated
@Tag(name = "Фукнции для лидера по манипуляции экспедициями")
public class ExpeditionManipulationByLeaderController {

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

    @DeleteMapping("/{expeditionId}")
    @PreAuthorize("hasRole('LEADER') and @expeditionSecurity.isLeaderOfExpedition(authentication, #expeditionId)")
    @Operation(summary = "Удалить экспедицию по id")
    public ResponseEntity<Void> deleteExpedition(
            @PathVariable("expeditionId") Long expeditionId,
            @AuthenticationPrincipal User currentUser) {

        log.debug("Leader {} deleting expedition {} STARTED", currentUser.getId(), expeditionId);
        expeditionService.deleteExpeditionById(expeditionId);
        log.debug("Leader {} deleting expedition {} ENDED", currentUser.getId(), expeditionId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('LEADER') and @expeditionSecurity.isLeaderOfExpedition(authentication, #expeditionId)")
    @GetMapping("/{expeditionId}/participants")
    @Operation(summary = "Получить участников экспедиции по id")
    public ResponseEntity<List<ParticipantResponse>> getExpeditionParticipants(
            @PathVariable("expeditionId") Long expeditionId,
            @AuthenticationPrincipal User currentUser) {

        log.debug("Leader {} VIEWING participants of expedition {}",
                currentUser.getId(), expeditionId);
        List<ParticipantResponse> participants = participantService.getExpeditionParticipants(
                expeditionId);
        return ResponseEntity.ok(participants);
    }

    @PostMapping("/{expeditionId}/participants")
    @PreAuthorize("hasRole('LEADER') and @expeditionSecurity.isLeaderOfExpedition(authentication, #expeditionId)")
    @Operation(summary = "Добавить нового участника экспедиции по индивидуальному номеру")
    public ResponseEntity<ParticipantResponse> addParticipant(
            @PathVariable("expeditionId") Long expeditionId,
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
            @PathVariable("expeditionId") Long participantId,
            @AuthenticationPrincipal User currentUser) {

        log.debug("Leader {} removing participant {} from expedition {} STARTED",
                currentUser.getId(), participantId, expeditionId);
        participantService.removeParticipant(expeditionId, participantId);
        log.debug("Leader {} removing participant {} from expedition {} ENDED",
                currentUser.getId(), participantId, expeditionId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{expeditionId}")
    @PreAuthorize("hasRole('LEADER') and @expeditionSecurity.isLeaderOfExpedition(authentication, #expeditionId)")
    @Operation(summary = "Редактирование экспедиции")
    public ResponseEntity<?> editExpedition(@PathVariable("expeditionId") Long expeditionId, @AuthenticationPrincipal User currentUser,
                                            @RequestBody @Valid EditExpeditionRequest request){
        log.debug("EXPEDITION-CONTROLLER: Started editing expedition");
        if (!expeditionService.isLeaderOfExpedition(expeditionId, currentUser.getId())) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Пользователь не является лидером данной экспедиции");
        Expedition expedition = expeditionService.editExpedition(expeditionId, request);
        log.debug("EXPEDITION-CONTROLLER: Ended editing expedition");
        return ResponseEntity.ok(ExpeditionResponse.mapFromEntityToResponse(expedition));
    }

}
