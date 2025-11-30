package com.arctic.backend_for_arctic_team.dto.request;

public record RegisterRequest(
        String firstName,
        String secondName,
        String email,
        String password
) {
}
