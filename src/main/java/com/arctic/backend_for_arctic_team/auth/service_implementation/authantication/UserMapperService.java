package com.arctic.backend_for_arctic_team.auth.service_implementation.authantication;


import com.arctic.backend_for_arctic_team.auth.dto.reponse.admin_responses.UserWithRolesResponse;
import com.arctic.backend_for_arctic_team.auth.dto.reponse.auth_responses.RegisterResponse;
import com.arctic.backend_for_arctic_team.auth.dto.request.auth_requests.RegisterRequest;
import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.auth.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserMapperService {
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse mapFromEntityToResponse(User user) {
        return new RegisterResponse(
                user.getEmail(),
                user.getIndividualNumber(),
                user.getCreatedAt()
        );
    }

    public User mapFromRequestToEntity(RegisterRequest request) {
        return User.builder()
                .email(request.email())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .password(passwordEncoder.encode(request.password()))
                .roles(Set.of(UserRole.ROLE_USER))
                .build();
    }
    public UserWithRolesResponse mapToUserWithRolesResponse(User user) {
        return new UserWithRolesResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getIndividualNumber(),
                user.getCreatedAt(),
                user.getRoles(),
                user.isEnabled(),
                user.isAccountNonLocked(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired()
        );
    }
}
