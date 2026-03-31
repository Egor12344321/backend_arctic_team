package com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation.exceptions;

import com.arctic.backend_for_arctic_team.expedition.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class MetricsForAnalyticsNotFountException extends ApiException {
    public MetricsForAnalyticsNotFountException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }
}
