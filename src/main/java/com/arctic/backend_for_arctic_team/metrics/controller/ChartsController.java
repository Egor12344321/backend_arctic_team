package com.arctic.backend_for_arctic_team.metrics.controller;


import com.arctic.backend_for_arctic_team.expedition.model.dto.charts.ParticipantChartsDto;
import com.arctic.backend_for_arctic_team.metrics.service.charts.ChartsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@Tag(name = "Графики API")
@RequestMapping("/api/v1/charts")
public class ChartsController {

    private final ChartsService chartsService;

    @GetMapping( "/{expeditionId}/{indNum}")
    // получение графиков, если пользователь лидер запрашиваемой экспедиции или участник запрашиваемой экспедиции
    // получение либо всех графиков, либо конкретно указанных в requestparam
    @PreAuthorize("hasRole('LEADER') and @expeditionSecurity.isLeaderOfExpedition(authentication, #expeditionId) or hasRole('USER') and @expeditionSecurity.isParticipantOfExpedition(authentication, #expeditionId)")
    @Operation(summary = "Получить все графики участника экспедиции, либо выбрать несколько")
    public ResponseEntity<ParticipantChartsDto> getParticipantCharts(@PathVariable Long expeditionId, @PathVariable String indNum, @RequestParam(required = false) List<String> type) {

        log.debug("Loading charts for indNum={} in expedition={}", indNum, expeditionId);

        ParticipantChartsDto charts = chartsService.getParticipantCharts(indNum, expeditionId, type);
        return ResponseEntity.ok(charts);
    }

    @GetMapping(value = "/expeditions/{expeditionId}/{chartType}", produces = MediaType.IMAGE_PNG_VALUE)
    @PreAuthorize("hasRole('LEADER') and @expeditionSecurity.isLeaderOfExpedition(authentication, #expeditionId) or hasRole('USER') and @expeditionSecurity.isParticipantOfExpedition(authentication, #expeditionId)")
    @Operation(summary = "Получение конкретного графика по типу")
    public ResponseEntity<byte[]> getChartImage(@PathVariable Long expeditionId, @PathVariable String chartType, @RequestParam @NotBlank String indNum
    ) {
        byte[] image = chartsService.getSingleChart(chartType, indNum, expeditionId);
        if (image == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(image.length)
                .body(image);
    }
}
