package com.arctic.backend_for_arctic_team.auth.dto.reponse.auth_responses;

import com.arctic.backend_for_arctic_team.auth.entity.UserRole;

import java.util.Set;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        String username,
        String individualNumber,
        String firstName,
        String lastName,
        Set<UserRole> userRole
) {

}
