package com.arctic.backend_for_arctic_team.metrics.service.charts;

import com.arctic.backend_for_arctic_team.metrics.model.dto.response.data_for_visualisation.SessionMetricsDto;
import com.arctic.backend_for_arctic_team.metrics.repository.jdbc.DashboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DashboardRepository repository;

    public List<SessionMetricsDto> getDashboardData(String indNum, Long expId) {
        return repository.getDashboardData(indNum, expId);
    }
}