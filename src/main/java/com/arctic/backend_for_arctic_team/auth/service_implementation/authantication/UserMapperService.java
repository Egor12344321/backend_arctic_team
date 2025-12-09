package com.arctic.backend_for_arctic_team.auth.service_implementation.authantication;


import com.arctic.backend_for_arctic_team.auth.dto.reponse.auth_responses.RegisterResponse;
import com.arctic.backend_for_arctic_team.auth.dto.request.auth_requests.RegisterRequest;
import com.arctic.backend_for_arctic_team.auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
                .build();
    }
}
