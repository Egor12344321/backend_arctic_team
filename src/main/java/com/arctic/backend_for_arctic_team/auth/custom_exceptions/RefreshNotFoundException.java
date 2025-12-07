package com.arctic.backend_for_arctic_team.auth.custom_exceptions;

public class RefreshNotFoundException extends RuntimeException {
    public RefreshNotFoundException(String message) {
        super(message);
    }
}
