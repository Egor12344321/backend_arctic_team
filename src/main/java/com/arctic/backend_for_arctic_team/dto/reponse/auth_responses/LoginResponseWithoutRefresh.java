package com.arctic.backend_for_arctic_team.dto.reponse.auth_responses;

public record LoginResponseWithoutRefresh(
        String accessToken,
        String username,
        String individualNumber,
        String firstName,
        String lastName
) {
    public LoginResponseWithoutRefresh(LoginResponse response){
        this(
                response.accessToken(),
                response.firstName(),
                response.lastName(),
                response.individualNumber(),
                response.username()
        );
    }
}
