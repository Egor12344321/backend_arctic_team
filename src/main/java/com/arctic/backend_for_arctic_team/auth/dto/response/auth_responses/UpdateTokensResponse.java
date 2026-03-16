package com.arctic.backend_for_arctic_team.auth.dto.response.auth_responses;

public record UpdateTokensResponse(
        String accessToken,
        String refreshToken,
        String username,
        String individualNumber
) {
}
