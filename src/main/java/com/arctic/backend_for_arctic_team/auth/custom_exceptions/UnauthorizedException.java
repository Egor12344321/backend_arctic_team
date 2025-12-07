package com.arctic.backend_for_arctic_team.auth.custom_exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
