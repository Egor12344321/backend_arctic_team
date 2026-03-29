package com.arctic.backend_for_arctic_team.metrics.service.analytics.python_implementation;

import com.arctic.backend_for_arctic_team.expedition.exceptions.PythonClientException;
import com.arctic.backend_for_arctic_team.expedition.model.dto.response.AnalyticsAdviceResponse;
import com.arctic.backend_for_arctic_team.metrics.service.analytics.AnalyticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "analytics.provider", havingValue = "python", matchIfMissing = true)
public class AnalyticsServicePythonImpl implements AnalyticsService {

    private final RestClient pythonRestClient;

    @Override
    public AnalyticsAdviceResponse getAnalyticsAdvice(String indNum, Long expeditionId) {
        log.info("Запрос аналитики от GigaChat для indNum: {}, expeditionId: {}", indNum, expeditionId);

        String uri = UriComponentsBuilder.fromPath("/advices/{ind_num}/{expedition_id}")
                .buildAndExpand(indNum, expeditionId)
                .toUriString();
        AnalyticsAdviceResponse response = pythonRestClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    String error = new String(res.getBody().readAllBytes());
                    log.error("Python сервис вернул ошибку: {}", error);
                    throw new PythonClientException("Ошибка в данных для аналитики", HttpStatus.BAD_REQUEST);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    log.error("Ошибка Python сервиса при получении аналитики для {} {}", indNum, expeditionId);
                    throw new PythonClientException("Сервис аналитики временно недоступен", HttpStatus.SERVICE_UNAVAILABLE);
                })
                .body(AnalyticsAdviceResponse.class);

        log.info("Успешно получен ответ от GigaChat для indNum: {}", indNum);
        return response;

        }
    }
