package com.arctic.backend_for_arctic_team.controller;


import com.arctic.backend_for_arctic_team.dto.reponse.RegisterResponse;
import com.arctic.backend_for_arctic_team.dto.request.RegisterRequest;
import com.arctic.backend_for_arctic_team.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    public ResponseEntity<RegisterResponse> register(RegisterRequest request){
        RegisterResponse response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
