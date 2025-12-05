package com.arctic.backend_for_arctic_team.controller;


import com.arctic.backend_for_arctic_team.dto.reponse.auth_responses.*;
import com.arctic.backend_for_arctic_team.dto.request.auth_requests.LoginRequest;
import com.arctic.backend_for_arctic_team.dto.request.auth_requests.RegisterRequest;

import com.arctic.backend_for_arctic_team.exceptions.custom_exceptions.RefreshNotFoundException;
import com.arctic.backend_for_arctic_team.service_implementation.util.CookieUtil;
import com.arctic.backend_for_arctic_team.service_interface.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final CookieUtil cookieUtil;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request){
        log.debug("CONTROLLER: Started registration for user: {}", request.email());
        RegisterResponse response = authService.register(request);
        log.debug("CONTROLLER: Ended registration for user: {}", request.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseWithoutRefresh> login(@Valid @RequestBody LoginRequest request){
        log.debug("CONTROLLER: Started login for user: {}", request.email());
        System.out.println("LOGIN");
        LoginResponse response = authService.login(request);
        ResponseCookie responseCookie = cookieUtil.setRefreshTokenToCookie(response.refreshToken());
        LoginResponseWithoutRefresh responseWithoutRefresh = new LoginResponseWithoutRefresh(response);
        log.debug("CONTROLLER: Ended login for user: {}", request.email());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(responseWithoutRefresh);
    }

    @PostMapping("/refresh")
    public ResponseEntity<UpdateTokensWithoutRefresh> refresh(@CookieValue(value = "refresh", required = false) String refreshToken){
        log.debug("CONTROLLER: Updating tokens started");
        if (refreshToken == null || refreshToken.isEmpty()) {
            log.error("CONTROLLER: Refresh not in cookie header");
            throw new RefreshNotFoundException("Refresh токен не найден в cookie");
        }

        UpdateTokensResponse updateTokensResponse = authService.refresh(refreshToken);
        ResponseCookie responseCookie = cookieUtil.setRefreshTokenToCookie(updateTokensResponse.refreshToken());
        UpdateTokensWithoutRefresh updateTokensWithoutRefresh = new UpdateTokensWithoutRefresh(updateTokensResponse);
        log.debug("CONTROLLER: Ended tokens started");

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(updateTokensWithoutRefresh);

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(name = "refresh") String refreshToken, @RequestHeader(name = "Authorization") String accessWithBearer){
        log.debug("CONTROLLER: Started logout");
        String accessToken = accessWithBearer.substring(7);

        try {
            if (refreshToken != null) {
                authService.logout(accessToken, refreshToken);
            }

            ResponseCookie deleteCookie = cookieUtil.deleteFromCookie("refresh");
            log.info("Refresh удален из cookie");
            log.debug("CONTROLLER: Ended logout");
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                    .build();

        } catch (Exception e) {
            ResponseCookie deleteCookie = cookieUtil.deleteFromCookie("refresh");
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                    .build();
        }
    }


}
