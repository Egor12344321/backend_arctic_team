package com.arctic.backend_for_arctic_team.auth.custom_exceptions;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private final HttpStatus errorCode;

    public ApiException(String message, HttpStatus errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}