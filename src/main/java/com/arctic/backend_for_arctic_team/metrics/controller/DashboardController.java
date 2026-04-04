package com.arctic.backend_for_arctic_team.metrics.controller;

import com.arctic.backend_for_arctic_team.metrics.model.dto.response.data_for_visualisation.SessionMetricsDto;
import com.arctic.backend_for_arctic_team.metrics.repository.jdbc.DashboardRepository;
import com.arctic.backend_for_arctic_team.metrics.service.charts.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/{indNum}/{expId}")
    public ResponseEntity<List<SessionMetricsDto>> getDashboard(@PathVariable String indNum, @PathVariable Long expId) {

        return ResponseEntity.ok(dashboardService.getDashboardData(indNum, expId));
    }
}