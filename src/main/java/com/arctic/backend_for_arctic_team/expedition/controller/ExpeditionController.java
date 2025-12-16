package com.arctic.backend_for_arctic_team.expedition.controller;


import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.auth.security.UserDetailsImpl;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.AddParticipantRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.CreateExpeditionRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.request.UpdateExpeditionRequest;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.ExpeditionResponse;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.ParticipantResponse;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.UserExpeditionResponse;
import com.arctic.backend_for_arctic_team.expedition.repository.ParticipantRepository;
import com.arctic.backend_for_arctic_team.expedition.service.ChartsService;
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
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/expeditions")
public class ExpeditionController {
    private final ExpeditionService expeditionService;
    private final ParticipantService participantService;
    private final ChartsService chartsService;

    @PostMapping
    @PreAuthorize("hasRole('LEADER')")
    public ResponseEntity<ExpeditionResponse> createExpedition(
            @Valid @RequestBody CreateExpeditionRequest request,
            @AuthenticationPrincipal User currentUser) {

        log.debug("EXPEDITION-CONTROLLER: Leader {} creating expedition: {}", currentUser.getId(), request.name());
        ExpeditionResponse expedition = expeditionService.createExpedition(request, currentUser);
        log.debug("EXPEDITION-CONTROLLER: Leader {} creating expedition: {} ENDED", currentUser.getId(), request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(expedition);
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('USER', 'LEADER', 'ADMIN')")
    public ResponseEntity<UserExpeditionResponse> getMyExpeditions(
            @AuthenticationPrincipal User currentUser) {

        log.info("User {} requesting their expeditions", currentUser.getId());

        UserExpeditionResponse expeditions = expeditionService.getUserExpeditions(currentUser);
        return ResponseEntity.ok(expeditions);
    }

//    @GetMapping("/{expeditionId}")
//    public ResponseEntity<ExpeditionResponse> getExpeditionDetails(
//            @PathVariable Long expeditionId,
//            @AuthenticationPrincipal User currentUser) {
//
//        log.info("User {} requesting expedition {}", currentUser.getId(), expeditionId);
//        ExpeditionResponse details = expeditionService.getExpeditionDetails(expeditionId, currentUser);
//        return ResponseEntity.ok(details);
//    }
//
//
//    @DeleteMapping("/{expeditionId}")
//    public ResponseEntity<Void> deleteExpedition(
//            @PathVariable Long expeditionId,
//            @AuthenticationPrincipal User currentUser) {
//
//        log.info("Leader {} deleting expedition {}", currentUser.getId(), expeditionId);
//        expeditionService.deleteExpedition(expeditionId, currentUser);
//        return ResponseEntity.noContent().build();
//    }

    @PreAuthorize("hasRole('LEADER')")
    @GetMapping("/{expeditionId}/participants")
    public ResponseEntity<List<ParticipantResponse>> getExpeditionParticipants(
            @PathVariable Long expeditionId,
            @AuthenticationPrincipal User currentUser) {

        log.info("Leader {} viewing participants of expedition {}",
                currentUser.getId(), expeditionId);
        List<ParticipantResponse> participants = participantService.getExpeditionParticipants(
                expeditionId);
        return ResponseEntity.ok(participants);
    }

    @PostMapping("/{expeditionId}/participants")
    @PreAuthorize("hasRole('LEADER')")
    public ResponseEntity<ParticipantResponse> addParticipant(
            @PathVariable Long expeditionId,
            @Valid @RequestBody AddParticipantRequest request,
            @AuthenticationPrincipal User currentUser) {

        log.info("Leader {} adding participant {} to expedition {}",
                currentUser.getId(), request.individualNumber(), expeditionId);
        ParticipantResponse participant = participantService.addParticipant(
                expeditionId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(participant);
    }

    @DeleteMapping("/{expeditionId}/participants/{participantId}")
    @PreAuthorize("hasRole('LEADER')")
    public ResponseEntity<Void> removeParticipant(
            @PathVariable Long expeditionId,
            @PathVariable Long participantId,
            @AuthenticationPrincipal User currentUser) {

        log.info("Leader {} removing participant {} from expedition {}",
                currentUser.getId(), participantId, expeditionId);
        participantService.removeParticipant(expeditionId, participantId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{expeditionId}/charts/my")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getParticipantCharts(
            @PathVariable Long expeditionId, @AuthenticationPrincipal User user){
        String individualNumber = user.getIndividualNumber();

        log.debug("EXPEDITION-CONTROLLER: Getting participant: {} charts started", individualNumber);
        Map<String, Object> charts = chartsService.getUserCharts(individualNumber, expeditionId, user);
        log.debug("EXPEDITION-CONTROLLER: Getting participant: {} charts ended", individualNumber);

        return ResponseEntity.ok(charts);
    }
}
