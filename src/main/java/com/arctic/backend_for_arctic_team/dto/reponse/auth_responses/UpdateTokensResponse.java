package com.arctic.backend_for_arctic_team.dto.reponse.auth_responses;

public record UpdateTokensResponse(
        String accessToken,
        String refreshToken,
        String username,
        String individualNumber
) {
}
