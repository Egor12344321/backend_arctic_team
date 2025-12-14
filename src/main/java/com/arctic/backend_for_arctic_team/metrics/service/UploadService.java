package com.arctic.backend_for_arctic_team.metrics.service;

import com.arctic.backend_for_arctic_team.metrics.model.dto.request.UploadRequest;
import com.arctic.backend_for_arctic_team.metrics.model.dto.response.UploadResponse;

public interface UploadService {
    void uploadMetrics(UploadRequest uploadRequest);
}
