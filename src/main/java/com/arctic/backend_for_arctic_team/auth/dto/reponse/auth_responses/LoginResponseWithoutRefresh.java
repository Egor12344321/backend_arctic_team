package com.arctic.backend_for_arctic_team.auth.dto.reponse.auth_responses;

import com.arctic.backend_for_arctic_team.auth.entity.UserRole;

import java.util.Set;

public record LoginResponseWithoutRefresh(
        String accessToken,
        String username,
        String individualNumber,
        String firstName,
        String lastName,
        Set<UserRole> userRoles
) {
    public LoginResponseWithoutRefresh(LoginResponse response){
        this(
                response.accessToken(),
                response.firstName(),
                response.lastName(),
                response.individualNumber(),
                response.username(),
                response.userRoles()
        );
    }
}
