package com.arctic.backend_for_arctic_team.controller;


import com.arctic.backend_for_arctic_team.dto.reponse.LoginResponse;
import com.arctic.backend_for_arctic_team.dto.reponse.LoginResponseWithoutRefresh;
import com.arctic.backend_for_arctic_team.dto.reponse.RegisterResponse;
import com.arctic.backend_for_arctic_team.dto.request.LoginRequest;
import com.arctic.backend_for_arctic_team.dto.request.RegisterRequest;

import com.arctic.backend_for_arctic_team.service_interface.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request){
        RegisterResponse response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseWithoutRefresh> login(@Valid @RequestBody LoginRequest request){
        LoginResponse response = userService.login(request);
        ResponseCookie responseCookie = userService.setRefreshTokenToCookie(response.refreshToken());
        LoginResponseWithoutRefresh responseWithoutRefresh = new LoginResponseWithoutRefresh(response);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(responseWithoutRefresh);
    }



}
