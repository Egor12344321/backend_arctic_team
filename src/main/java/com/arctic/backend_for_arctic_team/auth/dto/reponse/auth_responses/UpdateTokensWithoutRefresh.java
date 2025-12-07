package com.arctic.backend_for_arctic_team.auth.dto.reponse.auth_responses;

public record UpdateTokensWithoutRefresh(
        String accessToken,
        String username,
        String individualNumber
) {
    public UpdateTokensWithoutRefresh(UpdateTokensResponse response){
        this(
                response.accessToken(),
                response.individualNumber(),
                response.username()
        );
    }
}
