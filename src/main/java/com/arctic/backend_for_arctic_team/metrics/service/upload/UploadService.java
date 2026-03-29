package com.arctic.backend_for_arctic_team.metrics.service.upload;

import com.arctic.backend_for_arctic_team.metrics.model.dto.request.UploadRequest;
import com.arctic.backend_for_arctic_team.metrics.model.dto.response.UploadResponse;

public interface UploadService {
    UploadResponse uploadMetrics(UploadRequest uploadRequest);
}
