package com.arctic.backend_for_arctic_team.expedition.controller;


import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.expedition.service.ChartsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@Tag(name = "Графики API")
public class ChartsController {

    private final ChartsService chartsService;

    @GetMapping("/expeditions/{expeditionId}")
    // получение графиков, если пользователь лидер запрашиваемой экспедиции или участник запрашиваемой экспедиции
    @PreAuthorize("hasRole('LEADER') and @expeditionSecurity.isLeaderOfExpedition(authentication, #expeditionId) or hasRole('USER') and @expeditionSecurity.isParticipantOfExpedition(authentication, #expeditionId)")
    @Operation(summary = "Получить графики участника экспедиции")
    public ResponseEntity<Map<String, Object>> getParticipantCharts(
            @PathVariable("expeditionId") Long expeditionId,
            @RequestParam @NotBlank String indNum,
            @RequestParam(required = false) String type) {

        log.info("Loading charts for indNum={} in expedition={}", indNum, expeditionId);

        Map<String, Object> charts = chartsService.getParticipantCharts(indNum, expeditionId, type);
        return ResponseEntity.ok(charts);
    }

    @GetMapping("/expeditions/{expeditionId}/all")
    @PreAuthorize("@expeditionSecurity.isLeaderOfExpedition(authentication, #expeditionId)")
    @Operation(summary = "Получить ВСЕ графики всех участников")
    public ResponseEntity<Map<String, Map<String, Object>>> getAllExpeditionCharts(
            @PathVariable("expeditionId") Long expeditionId) {

        Map<String, Map<String, Object>> allCharts = chartsService.getAllExpeditionCharts(expeditionId);
        return ResponseEntity.ok(allCharts);
    }
}
