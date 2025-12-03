package com.arctic.backend_for_arctic_team.dto.request.auth_requests;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotNull(message = "Email обязателен.") String email,
        @NotNull(message = "Пароль обязателен.") String password
) {
}
