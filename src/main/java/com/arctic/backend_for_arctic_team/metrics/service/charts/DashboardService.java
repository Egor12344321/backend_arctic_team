package com.arctic.backend_for_arctic_team.metrics.service.charts;

import com.arctic.backend_for_arctic_team.metrics.model.dto.response.data_for_visualisation.SessionMetricsDto;
import com.arctic.backend_for_arctic_team.metrics.repository.jdbc.DashboardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardService {

    private final DashboardRepository repository;

    public List<SessionMetricsDto> getDashboardData(String indNum, Long expId) {
        log.info("Получены данные для визуализации метрик для indNum: {}, expId: {}", indNum, expId);
        return repository.getDashboardData(indNum, expId);
    }
}