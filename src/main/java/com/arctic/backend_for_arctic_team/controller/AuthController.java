package com.arctic.backend_for_arctic_team.controller;


import com.arctic.backend_for_arctic_team.dto.reponse.auth_responses.*;
import com.arctic.backend_for_arctic_team.dto.request.auth_requests.LoginRequest;
import com.arctic.backend_for_arctic_team.dto.request.auth_requests.RegisterRequest;

import com.arctic.backend_for_arctic_team.exceptions.custom_exceptions.RefreshNotFoundException;
import com.arctic.backend_for_arctic_team.service_implementation.util.CookieUtil;
import com.arctic.backend_for_arctic_team.service_interface.AuthService;
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
        RegisterResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseWithoutRefresh> login(@Valid @RequestBody LoginRequest request){
        LoginResponse response = authService.login(request);
        ResponseCookie responseCookie = cookieUtil.setRefreshTokenToCookie(response.refreshToken());
        LoginResponseWithoutRefresh responseWithoutRefresh = new LoginResponseWithoutRefresh(response);
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

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(updateTokensWithoutRefresh);

    }


}
