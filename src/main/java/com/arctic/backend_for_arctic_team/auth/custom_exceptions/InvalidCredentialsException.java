package com.arctic.backend_for_arctic_team.auth.custom_exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidCredentialsException extends RuntimeException   {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
