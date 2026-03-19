package com.arctic.backend_for_arctic_team.expedition.controller;


import com.arctic.backend_for_arctic_team.expedition.model.dto.response.AnalyticsAdviceResponse;
import com.arctic.backend_for_arctic_team.expedition.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/analytics")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Получение аналитики по данным с нейроинтерфейса")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/advices/{indNum}/{expeditionId}")
    @PreAuthorize("hasRole('LEADER') and @expeditionSecurity.isLeaderOfExpedition(authentication, #expeditionId) or hasRole('USER') and @expeditionSecurity.isParticipantOfExpedition(authentication, #expeditionId)")
    @Operation(summary = "Получить совет-вывод по полученным данным")
    public ResponseEntity<AnalyticsAdviceResponse> getAnalyticsAdvice(@PathVariable String indNum, @PathVariable Long expeditionId){
        log.info("Started getting advice for user with indNum: {}, expeditionId: {}", indNum, expeditionId);

        AnalyticsAdviceResponse response = analyticsService.getAnalyticsAdvice(indNum, expeditionId);

        log.info("Ended getting advice for user with indNum: {}, expeditionId: {}", indNum, expeditionId);

        return ResponseEntity.ok().body(response);
    }
}
