package com.arctic.backend_for_arctic_team.service_implementation;

import com.arctic.backend_for_arctic_team.dto.reponse.LoginResponse;
import com.arctic.backend_for_arctic_team.dto.reponse.RegisterResponse;
import com.arctic.backend_for_arctic_team.dto.request.LoginRequest;
import com.arctic.backend_for_arctic_team.dto.request.RegisterRequest;
import com.arctic.backend_for_arctic_team.entity.User;
import com.arctic.backend_for_arctic_team.exceptions.custom_exceptions.InvalidCredentialsException;
import com.arctic.backend_for_arctic_team.repository.UserRepository;
import com.arctic.backend_for_arctic_team.security.JwtUtil;
import com.arctic.backend_for_arctic_team.security.UserDetailsImpl;
import com.arctic.backend_for_arctic_team.service_implementation.cache.RedisCacheServiceImpl;
import com.arctic.backend_for_arctic_team.service_interface.CacheService;
import com.arctic.backend_for_arctic_team.service_interface.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.Cookie;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserMapperService userMapperService;
    private final CacheService cacheService;

    public RegisterResponse register(RegisterRequest request){
        User user = userMapperService.mapFromRequestToEntity(request);
        User savedUser = userRepository.save(user);
        return userMapperService.mapFromEntityToResponse(user);
    }

    public LoginResponse login(LoginRequest request) {
        log.debug("USER-SERVICE: Login started");
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
            if (authentication.getPrincipal() == null) {
                log.error("Authentication returned null principal");
                throw new AuthenticationCredentialsNotFoundException("Authentication failed");
            }
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            if (!userDetails.isEnabled()) {
                throw new DisabledException("User account is disabled");
            }
            String email = userDetails.getUsername();
            log.info("USER-SERVICE: User authenticated: {}", email);
            String accessToken = jwtUtil.generateAccessToken(userDetails);
            log.info("AccessToken generated successfully");
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);
            log.info("RefreshToken generated successfully");
            cacheService.saveToCache(userDetails.getIndividualNimber(), refreshToken);

            return new LoginResponse(
                    accessToken,
                    refreshToken,
                    userDetails.getUsername(),
                    userDetails.getIndividualNimber(),
                    userDetails.getFirstName(),
                    userDetails.getLastName()
            );
        } catch (AuthenticationException e){
            throw new InvalidCredentialsException("Неправильный логин или пароль");
        }
    }

    public ResponseCookie setRefreshTokenToCookie(String refreshToken) {
        return ResponseCookie.from("refresh", refreshToken)
                .httpOnly(true)
                .maxAge(7 * 24 * 60 * 60)
                .path("/api/auth/refresh")
                .build();
    }
}
