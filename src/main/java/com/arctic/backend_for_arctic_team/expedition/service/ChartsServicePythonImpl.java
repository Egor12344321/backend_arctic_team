package com.arctic.backend_for_arctic_team.expedition.service;


import com.arctic.backend_for_arctic_team.expedition.config.ChartsServiceProperties;
import com.arctic.backend_for_arctic_team.expedition.repository.ExpeditionRepository;
import com.arctic.backend_for_arctic_team.expedition.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChartsServicePythonImpl implements ChartsService {

    private final ChartsServiceProperties properties;
    private final ExpeditionRepository expeditionRepository;
    private final ParticipantRepository participantRepository;
    private final RestTemplate restTemplate;


    @Override
    public Map<String, Object> getParticipantCharts(String indNum, Long expeditionId, String type) {
        return Map.of();
    }

    @Override
    public Map<String, Map<String, Object>> getAllExpeditionCharts(Long expeditionId) {
        return Map.of();
    }
}


