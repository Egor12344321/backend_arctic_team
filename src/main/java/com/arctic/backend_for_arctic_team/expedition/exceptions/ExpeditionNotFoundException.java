package com.arctic.backend_for_arctic_team.expedition.exceptions;

public class ExpeditionNotFoundException extends RuntimeException {
    public ExpeditionNotFoundException(String message) {
        super(message);
    }
}
