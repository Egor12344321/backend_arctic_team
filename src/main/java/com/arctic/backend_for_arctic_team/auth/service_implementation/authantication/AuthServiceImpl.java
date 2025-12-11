package com.arctic.backend_for_arctic_team.auth.service_implementation.authantication;

import com.arctic.backend_for_arctic_team.auth.dto.reponse.auth_responses.LoginResponse;
import com.arctic.backend_for_arctic_team.auth.dto.reponse.auth_responses.RegisterResponse;
import com.arctic.backend_for_arctic_team.auth.dto.reponse.auth_responses.UpdateTokensResponse;
import com.arctic.backend_for_arctic_team.auth.dto.request.auth_requests.LoginRequest;
import com.arctic.backend_for_arctic_team.auth.dto.request.auth_requests.RegisterRequest;
import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.auth.custom_exceptions.InvalidCredentialsException;
import com.arctic.backend_for_arctic_team.auth.custom_exceptions.InvalidTokenRefreshException;
import com.arctic.backend_for_arctic_team.auth.custom_exceptions.RefreshNotFoundException;
import com.arctic.backend_for_arctic_team.auth.custom_exceptions.UserNotFoundException;
import com.arctic.backend_for_arctic_team.auth.repository.UserRepository;
import com.arctic.backend_for_arctic_team.auth.security.JwtUtil;
import com.arctic.backend_for_arctic_team.auth.security.UserDetailsImpl;
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
        User savedUser = userRepository.save(user);
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
            log.info("USER-SERVICE: User authenticated: {}", email);
            String accessToken = jwtUtil.generateAccessToken(userDetails);
            log.info("AccessToken generated successfully");
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);
            log.info("RefreshToken generated successfully");
            cacheService.saveToCache(userDetails.getIndividualNumber(), refreshToken);

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
        log.info("Токен из кук найден и является валидным для пользователя: {}", email);
        log.info("Токен из cache найден и является валидным для пользователя: {}", jwtUtil.extractUsername(refreshTokenFromCache));
        log.info("Совпадение токенов из cache и cookie: {}", refreshToken.equals(refreshTokenFromCache));
        if(refreshTokenFromCache.equals(refreshToken)) {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("Пользователь с таким email не найден"));
            log.info("Пользователь с таким refresh найден");


            cacheService.removeFromCache(individualNumber);

            String updatedRefreshToken = jwtUtil.generateRefreshToken(user);
            String updatedAccessToken = jwtUtil.generateAccessToken(user);

            cacheService.saveToCache(individualNumber, updatedRefreshToken);
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
        log.info("Access token saved to blacklist");
        String individualNumber = jwtUtil.extractIndividualNumber(refreshToken);
        log.info("Individual number for deleting refresh: {}", individualNumber);
        cacheService.removeFromCache(individualNumber);
    }



}
