package com.arctic.backend_for_arctic_team.expedition.service;

import com.arctic.backend_for_arctic_team.expedition.exceptions.PythonClientException;
import com.arctic.backend_for_arctic_team.expedition.model.dto.charts.ChartDto;
import com.arctic.backend_for_arctic_team.expedition.model.dto.charts.ParticipantChartsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChartsServicePythonImpl implements ChartsService {

    private final RestClient pythonRestClient;

    @Override
    public ParticipantChartsDto getParticipantCharts(String indNum, Long expeditionId, List<String> chartTypes) {
        if (chartTypes == null) chartTypes = List.of("alpha-beta-theta", "fatigue", "heart-rate", "psychological-fatigue", "gravity", "concentration", "relaxation", "nfb");
        List<ChartDto> res = new ArrayList<>();
        for (String type : chartTypes){
            byte[] chartData = getSingleChart(type, indNum, expeditionId);
            ChartDto dto = new ChartDto(type, chartData);
            res.add(dto);
        }
        return new ParticipantChartsDto(indNum, expeditionId, res);
    }

    @Override
    public byte[] getSingleChart(String chartType, String indNum, Long expeditionId) {
        String uri = "/api/metrics/" + chartType + "/" + indNum + "/" + expeditionId;

        log.info("chartType: {}", chartType);
        log.info("indNum: {}", indNum);
        log.info("expeditionId: {}", expeditionId);
        log.info("URI: {}", uri);
        log.info("Полный URL: http://python-service:8000{}", uri);

        return pythonRestClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    String error = new String(res.getBody().readAllBytes());
                    log.error("Python вернул ошибку: {}", error);
                    throw new PythonClientException("Ошибка чтения ответа от Python: " + error, HttpStatus.BAD_REQUEST);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    log.error("Ошибка Python сервиса для {} {}", indNum, expeditionId);
                    log.error("Запрос был: {}", uri);
                    throw new PythonClientException("Сервис графиков временно недоступен", HttpStatus.SERVICE_UNAVAILABLE);
                })
                .body(byte[].class);
    }
}