package com.arctic.backend_for_arctic_team.service;

import com.arctic.backend_for_arctic_team.dto.reponse.RegisterResponse;
import com.arctic.backend_for_arctic_team.dto.request.RegisterRequest;
import com.arctic.backend_for_arctic_team.entity.User;
import com.arctic.backend_for_arctic_team.repository.UserRepository;
import com.arctic.backend_for_arctic_team.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public RegisterResponse register(RegisterRequest request){
        User user = mapFromRequestToEntity(request);
        User savedUser = userRepository.save(user);
        return mapFromEntityToResponse(user);
    }

    private RegisterResponse mapFromEntityToResponse(User user) {
        return new RegisterResponse(
                user.getEmail(),
                user.getIndividualNumber()
        );
    }

    private User mapFromRequestToEntity(RegisterRequest request) {
        return User.builder()
                .email(request.email())
                .firstName(request.firstName())
                .lastName(request.secondName())
                .password(passwordEncoder.encode(request.password()))
                .build();
    }



}
