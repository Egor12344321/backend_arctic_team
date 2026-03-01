package com.arctic.backend_for_arctic_team.metrics.controller;

import com.arctic.backend_for_arctic_team.metrics.model.dto.request.UploadRequest;
import com.arctic.backend_for_arctic_team.metrics.model.dto.response.UploadResponse;
import com.arctic.backend_for_arctic_team.metrics.service.UploadService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/metrics")
public class MetricsSyncV1Controller {

    private final UploadService uploadService;


    @PostMapping("/upload")
    @Operation(summary = "Выгрузка метрик")
    public ResponseEntity<UploadResponse> uploadMetrics(@Valid @RequestBody UploadRequest uploadRequest){
        log.debug("METRICS-UPLOAD-CONTROLLER: Uploading metrics starting");

        UploadResponse response = uploadService.uploadMetrics(uploadRequest);

        log.debug("METRICS-UPLOAD-CONTROLLER: Uploading metrics ended");

        return ResponseEntity.ok(response);
    }
}