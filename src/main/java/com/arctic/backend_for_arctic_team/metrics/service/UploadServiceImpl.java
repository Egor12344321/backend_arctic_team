package com.arctic.backend_for_arctic_team.metrics.service;


import com.arctic.backend_for_arctic_team.expedition.service.MapperService;
import com.arctic.backend_for_arctic_team.metrics.model.dto.request.PhysiologicalMetricDto;
import com.arctic.backend_for_arctic_team.metrics.model.dto.request.UploadRequest;
import com.arctic.backend_for_arctic_team.metrics.model.dto.response.UploadResponse;
import com.arctic.backend_for_arctic_team.metrics.service.metrics_upload_service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService{
    private final CardioMetricsUploadService cardioMetricsUploadService;
    private final EmotionalMetricsUploadService emotionalMetricsUploadService;
    private final MemsMetricsUploadService memsMetricsUploadService;
    private final NfbMetricsUploadService nfbMetricsUploadService;
    private final PhysiologicalMetricsUploadService physiologicalMetricsUploadService;
    private final ProductivityMetricsUploadService productivityMetricsUploadService;

    @Override
    public UploadResponse uploadMetrics(UploadRequest uploadRequest) {
        log.info("Mapping from dto to entity");
        try {
            cardioMetricsUploadService.upload(uploadRequest.cardioMetrics());
            emotionalMetricsUploadService.upload(uploadRequest.emotionalMetrics());
            memsMetricsUploadService.upload(uploadRequest.memsMetrics());
            nfbMetricsUploadService.upload(uploadRequest.nfbMetrics());
            physiologicalMetricsUploadService.upload(uploadRequest.physiologicalMetrics());
            productivityMetricsUploadService.upload(uploadRequest.productivityMetrics());
            return new UploadResponse(true);
        } catch (Exception e){
            return new UploadResponse(false);
        }
    }
}
