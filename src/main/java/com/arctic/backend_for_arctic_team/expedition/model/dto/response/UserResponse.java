package com.arctic.backend_for_arctic_team.expedition.model.dto.response;

public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String individualNumber,
        String role
) {
}
