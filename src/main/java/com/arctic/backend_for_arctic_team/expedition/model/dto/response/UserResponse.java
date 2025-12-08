package com.arctic.backend_for_arctic_team.expedition.model.dto.response;

import com.arctic.backend_for_arctic_team.auth.entity.User;

public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String individualNumber
) {
    public static UserResponse mapFromEntityToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getIndividualNumber()
        );
    }
}
