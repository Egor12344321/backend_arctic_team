package com.arctic.backend_for_arctic_team.auth.dto.reponse.user_responses;

import com.arctic.backend_for_arctic_team.auth.entity.User;

public record UserSearchResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String individualNumber
) {
    public static UserSearchResponse fromUser(User user) {
        return new UserSearchResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getIndividualNumber()
        );
    }
}