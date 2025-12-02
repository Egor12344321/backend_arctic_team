package com.arctic.backend_for_arctic_team.dto.reponse;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        String username,
        String individualNumber,
        String firstName,
        String lastName
) {

}
