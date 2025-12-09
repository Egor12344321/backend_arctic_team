package com.arctic.backend_for_arctic_team.auth.dto.reponse.error_responces;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        String message,
        int status,
        LocalDateTime timestamp,
        List<ValidationError> errors
) {
    public ErrorResponse(String message, int status, LocalDateTime timestamp) {
        this(message, status, timestamp, null);
    }
}