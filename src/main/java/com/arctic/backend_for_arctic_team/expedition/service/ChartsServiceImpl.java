package com.arctic.backend_for_arctic_team.expedition.service;


import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.expedition.exceptions.ExpeditionNotFoundException;
import com.arctic.backend_for_arctic_team.expedition.exceptions.UserNotParticipantException;
import com.arctic.backend_for_arctic_team.expedition.model.entity.Expedition;
import com.arctic.backend_for_arctic_team.expedition.model.entity.Participant;
import com.arctic.backend_for_arctic_team.expedition.repository.ExpeditionRepository;
import com.arctic.backend_for_arctic_team.expedition.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChartsServiceImpl implements ChartsService {
    private static final String PYTHON_SERVICE_URL = "http://localhost:8000/api/charts/user";
    private final ExpeditionRepository expeditionRepository;
    private final ParticipantRepository participantRepository;
    private final RestTemplate restTemplate;

    @Override
    public Map<String, Object> getUserCharts(String individualNumber, Long expeditionId, User user) {
        log.info("CHARTS-SERVICE: Started getting charts for user: {}", individualNumber);

        Expedition expedition = expeditionRepository.findById(expeditionId)
                .orElseThrow(() -> new ExpeditionNotFoundException("Такой экспедиции не существует"));


        if (!participantRepository.existsByUserIdAndExpeditionId(user.getId(), expeditionId)){
            throw new UserNotParticipantException("Вы не являетесь участником экпедиции");
        }

        try {
            long startTimestamp = convertToStartOfDayTimestamp(expedition.getStartDate());
            long endTimestamp = convertToEndOfDayTimestamp(expedition.getEndDate());

            String url = String.format("%s/%s?start_timestamp=%d&end_timestamp=%d",
                    PYTHON_SERVICE_URL,
                    individualNumber,
                    startTimestamp,
                    endTimestamp
            );

            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Map.of(
                "individual_number", individualNumber,
                "message", "Не удалось получить графики",
                "charts", Map.of()
        );
        }
    public Map<String, Object> getParticipantCharts(Long participantId, Long expeditionId, User currentUser) {
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Участник не найден"));

        if (!participant.getExpedition().getLeader().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Доступ к метрикам участника запрещен");
        }

        String individualNumber = participant.getUser().getIndividualNumber();
        return getUserCharts(individualNumber, expeditionId, currentUser);
    }
    private long convertToStartOfDayTimestamp(LocalDate date) {
        return date.atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    private long convertToEndOfDayTimestamp(LocalDate date) {
        return date.atTime(LocalTime.MAX)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }
}


