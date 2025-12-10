package com.arctic.backend_for_arctic_team.auth.dto.request.admin_requests;

import com.arctic.backend_for_arctic_team.auth.entity.UserRole;

import java.util.Set;

public record AddRolesRequest(
        Set<UserRole>roles
) {
}
