package com.arctic.backend_for_arctic_team.auth.dto.reponse.admin_responses;

import com.arctic.backend_for_arctic_team.auth.entity.UserRole;

import java.time.LocalDateTime;
import java.util.Set;

public record UserWithRolesResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String individualNumber,
        LocalDateTime createdAt,
        Set<UserRole> roles,
        boolean enabled,
        boolean accountNonLocked,
        boolean accountNonExpired,
        boolean credentialsNonExpired
) {
}
