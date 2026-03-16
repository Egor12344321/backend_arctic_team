package com.arctic.backend_for_arctic_team.auth.dto.response.error_responces;

public record ValidationError(
        String field,
        String message,
        String rejectedValue
) {
}
