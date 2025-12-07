package com.arctic.backend_for_arctic_team.auth.dto.reponse.auth_responses;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        String username,
        String individualNumber,
        String firstName,
        String lastName
) {

}
