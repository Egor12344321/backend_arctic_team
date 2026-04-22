package com.arctic.backend_for_arctic_team.auth.service_implementation.authentication;

import com.arctic.backend_for_arctic_team.auth.custom_exceptions.*;
import com.arctic.backend_for_arctic_team.auth.dto.response.auth_responses.LoginResponse;
import com.arctic.backend_for_arctic_team.auth.dto.response.auth_responses.RegisterResponse;
import com.arctic.backend_for_arctic_team.auth.dto.response.auth_responses.UpdateTokensResponse;
import com.arctic.backend_for_arctic_team.auth.dto.request.auth_requests.LoginRequest;
import com.arctic.backend_for_arctic_team.auth.dto.request.auth_requests.RegisterRequest;
import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.auth.repository.UserRepository;
import com.arctic.backend_for_arctic_team.auth.security.JwtUtil;
import com.arctic.backend_for_arctic_team.auth.service_interface.CacheService;
import com.arctic.backend_for_arctic_team.auth.service_interface.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserMapperService userMapperService;
    private final CacheService cacheService;
    private final RedisTemplate<String, String> redisTemplate;
    final static String ACCESS_PREFIX = "blacklistedAccess:";
    private final TokenBlackListedService tokenBlackListedService;

    public RegisterResponse register(RegisterRequest request){
        User user = userMapperService.mapFromRequestToEntity(request);
        if (userRepository.existsByEmail(request.email())){
            log.warn("Попытка создать аккаунт с существующим email");
            throw new UserAlreadyExistsException("Пользователь с таким email уже зарегистрирован в системе");
        }
        User savedUser = userRepository.save(user);
        log.info("Пользователь: {} (id={}) зарегистрировался в системе", savedUser.getEmail(), user.getId());
        return userMapperService.mapFromEntityToResponse(savedUser);
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
            User userDetails = (User) authentication.getPrincipal();
            if (!userDetails.isEnabled()) {
                throw new DisabledException("User account is disabled");
            }
            String email = userDetails.getUsername();
            log.debug("USER-SERVICE: User authenticated: {}", email);
            String accessToken = jwtUtil.generateAccessToken(userDetails);
            log.debug("AccessToken generated successfully");
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);
            log.debug("RefreshToken generated successfully");
            cacheService.saveToCache(userDetails.getIndividualNumber(), refreshToken);
            log.debug("USER-DETAILS ROLES: {}", userDetails.getRoles());

            log.info("Пользователь: {} вошел в систему", email);
            return new LoginResponse(
                    accessToken,
                    refreshToken,
                    userDetails.getUsername(),
                    userDetails.getIndividualNumber(),
                    userDetails.getFirstName(),
                    userDetails.getLastName(),
                    userDetails.getRoles()
            );
        } catch (AuthenticationException e){
            throw new InvalidCredentialsException("Неправильный логин или пароль");
        }
    }


    public UpdateTokensResponse refresh(String refreshToken) {
        log.debug("USER-SERVICE: Updating tokens started");
        String individualNumber = jwtUtil.extractIndividualNumber(refreshToken);
        String email = jwtUtil.extractUsername(refreshToken);

        if (!jwtUtil.isRefresh(refreshToken)) {
            throw new InvalidTokenRefreshException("Полученный токен не refresh");
        }

        if (!jwtUtil.validateToken(refreshToken)){
            throw new InvalidTokenRefreshException("Срок действия рефршен токена истек, надо заново авторизовываться");
        }
        String refreshTokenFromCache = cacheService.getFromCache(individualNumber)
                .orElseThrow(() -> new RefreshNotFoundException("Refresh токен не найден для пользователя: " + individualNumber));
        if (!jwtUtil.isRefresh(refreshTokenFromCache)) {
            throw new InvalidTokenRefreshException("Полученный токен не refresh");
        }
        if (!jwtUtil.validateToken(refreshTokenFromCache)){
            throw new InvalidTokenRefreshException("Refresh токен из cache недействителен");
        }
        log.debug("Токен из кук найден и является валидным для пользователя: {}", email);
        log.debug("Токен из cache найден и является валидным для пользователя: {}", jwtUtil.extractUsername(refreshTokenFromCache));
        log.debug("Совпадение токенов из cache и cookie: {}", refreshToken.equals(refreshTokenFromCache));
        if(refreshTokenFromCache.equals(refreshToken)) {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("Пользователь с таким email не найден"));
            log.debug("Пользователь с таким refresh найден");


            cacheService.removeFromCache(individualNumber);

            String updatedRefreshToken = jwtUtil.generateRefreshToken(user);
            String updatedAccessToken = jwtUtil.generateAccessToken(user);

            cacheService.saveToCache(individualNumber, updatedRefreshToken);
            log.info("Пользователь: {} (id={}) обновил токены", user.getEmail(), user.getId());
            return new UpdateTokensResponse(
                    updatedAccessToken,
                    updatedRefreshToken,
                    email,
                    individualNumber
            );
        }
        log.error("Refresh token в cache и в cookie не совпадает");
        throw new InvalidTokenRefreshException("Refresh token в cache и в cookie не совпадает");
    }

    public void logout(String accessToken, String refreshToken) {
        log.debug("Started logout for user: {}", jwtUtil.extractUsername(accessToken));
        redisTemplate.opsForValue().set(ACCESS_PREFIX + tokenBlackListedService.generateTokenId(accessToken), accessToken, Duration.ofMinutes(30));
        log.debug("Access token saved to blacklist");
        String individualNumber = jwtUtil.extractIndividualNumber(refreshToken);
        log.debug("Individual number for deleting refresh: {}", individualNumber);
        log.info("Пользователь: {} вышел из системы", individualNumber);
        cacheService.removeFromCache(individualNumber);
    }



}
