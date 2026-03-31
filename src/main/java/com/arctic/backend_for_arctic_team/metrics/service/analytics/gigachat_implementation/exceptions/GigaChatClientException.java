package com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation.exceptions;

import com.arctic.backend_for_arctic_team.expedition.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class GigaChatClientException extends ApiException {
    public GigaChatClientException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }
}
