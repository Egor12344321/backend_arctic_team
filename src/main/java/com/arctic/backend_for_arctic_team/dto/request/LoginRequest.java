package com.arctic.backend_for_arctic_team.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotNull(message = "Email обязателен.") String email,
        @NotNull(message = "Пароль обязателен.") String password
) {
}
