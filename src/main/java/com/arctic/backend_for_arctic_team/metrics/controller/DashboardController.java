package com.arctic.backend_for_arctic_team.metrics.controller;

import com.arctic.backend_for_arctic_team.metrics.model.dto.response.data_for_visualisation.SessionMetricsDto;
import com.arctic.backend_for_arctic_team.metrics.service.charts.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/{indNum}/{expId}")
    @PreAuthorize("hasRole('LEADER') and @expeditionSecurity.isLeaderOfExpedition(authentication, #expId) or hasRole('USER') and @expeditionSecurity.isParticipantOfExpedition(authentication, #expId)")
    public ResponseEntity<List<SessionMetricsDto>> getDashboard(@PathVariable String indNum, @PathVariable Long expId) {
        log.debug("Запрос на получение графиков начался");
        return ResponseEntity.ok(dashboardService.getDashboardData(indNum, expId));
    }
}