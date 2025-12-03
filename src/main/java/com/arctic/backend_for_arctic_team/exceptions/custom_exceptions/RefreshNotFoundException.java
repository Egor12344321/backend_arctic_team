package com.arctic.backend_for_arctic_team.exceptions.custom_exceptions;

public class RefreshNotFoundException extends RuntimeException {
    public RefreshNotFoundException(String message) {
        super(message);
    }
}
