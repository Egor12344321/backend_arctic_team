package com.arctic.backend_for_arctic_team.dto.request.auth_requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotNull(message = "Имя обязательно для ввода") String firstName,
        @NotNull(message = "Фамилия обязательна для ввода") String secondName,
        @NotNull(message = "Email обязателен для ввода") @Email String email,
        @NotNull(message = "Пароль обязателен для ввода") @Size(min = 6, max = 30, message = "Пароль должен быть не менее 6 символов и не более 30") String password
) {
}
