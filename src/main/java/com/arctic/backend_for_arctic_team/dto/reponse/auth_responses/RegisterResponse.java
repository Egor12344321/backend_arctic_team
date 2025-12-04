package com.arctic.backend_for_arctic_team.dto.reponse.auth_responses;

import java.time.LocalDateTime;

public record RegisterResponse(
        String email,
        String individualNumber,
        LocalDateTime createdAt
) {
}
