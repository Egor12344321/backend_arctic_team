package com.arctic.backend_for_arctic_team.expedition.exceptions;

import org.springframework.http.HttpStatus;

public class PythonClientException extends ApiException {
    public PythonClientException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }
}
