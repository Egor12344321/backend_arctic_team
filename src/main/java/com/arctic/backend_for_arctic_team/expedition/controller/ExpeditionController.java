package com.arctic.backend_for_arctic_team.expedition.controller;


import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.auth.security.UserDetailsImpl;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.AddParticipantRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.CreateExpeditionRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.UpdateExpeditionRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.ExpeditionResponse;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.ParticipantResponse;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.UserExpeditionResponse;
import com.arctic.backend_for_arctic_team.expedition.service.ExpeditionService;
import com.arctic.backend_for_arctic_team.expedition.service.ParticipantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/expeditions")
public class ExpeditionController {
    private final ExpeditionService expeditionService;
    private final ParticipantService participantService;


    @PostMapping
    public ResponseEntity<ExpeditionResponse> createExpedition(
            @Valid @RequestBody CreateExpeditionRequest request,
            @AuthenticationPrincipal User currentUser) {

        log.info("Leader {} creating expedition: {}", currentUser.getId(), request.name());
        ExpeditionResponse expedition = expeditionService.createExpedition(request, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(expedition);
    }

    @GetMapping("/my")
    public ResponseEntity<UserExpeditionResponse> getMyExpeditions(
            @AuthenticationPrincipal UserDetailsImpl currentUser) {

        log.info("User {} requesting their expeditions", currentUser.getId());
        UserExpeditionResponse expeditions = expeditionService.getUserExpeditions(currentUser);
        return ResponseEntity.ok(expeditions);
    }

    @GetMapping("/{expeditionId}")
    public ResponseEntity<ExpeditionResponse> getExpeditionDetails(
            @PathVariable Long expeditionId,
            @AuthenticationPrincipal User currentUser) {

        log.info("User {} requesting expedition {}", currentUser.getId(), expeditionId);
        ExpeditionResponse details = expeditionService.getExpeditionDetails(expeditionId, currentUser);
        return ResponseEntity.ok(details);
    }


    @DeleteMapping("/{expeditionId}")
    public ResponseEntity<Void> deleteExpedition(
            @PathVariable Long expeditionId,
            @AuthenticationPrincipal User currentUser) {

        log.info("Leader {} deleting expedition {}", currentUser.getId(), expeditionId);
        expeditionService.deleteExpedition(expeditionId, currentUser);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{expeditionId}/participants")
    public ResponseEntity<List<ParticipantResponse>> getExpeditionParticipants(
            @PathVariable Long expeditionId,
            @AuthenticationPrincipal User currentUser) {

        log.info("Leader {} viewing participants of expedition {}",
                currentUser.getId(), expeditionId);
        List<ParticipantResponse> participants = participantService.getExpeditionParticipants(
                expeditionId, currentUser);
        return ResponseEntity.ok(participants);
    }

    @PostMapping("/{expeditionId}/participants")
    public ResponseEntity<ParticipantResponse> addParticipant(
            @PathVariable Long expeditionId,
            @Valid @RequestBody AddParticipantRequest request,
            @AuthenticationPrincipal User currentUser) {

        log.info("Leader {} adding participant {} to expedition {}",
                currentUser.getId(), request.individualNumber(), expeditionId);
        ParticipantResponse participant = participantService.addParticipant(
                expeditionId, request, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(participant);
    }

    @DeleteMapping("/{expeditionId}/participants/{userId}")
    public ResponseEntity<Void> removeParticipant(
            @PathVariable Long expeditionId,
            @PathVariable Long userId,
            @AuthenticationPrincipal User currentUser) {

        log.info("Leader {} removing participant {} from expedition {}",
                currentUser.getId(), userId, expeditionId);
        participantService.removeParticipant(expeditionId, userId, currentUser);
        return ResponseEntity.noContent().build();
    }

}
