package com.arctic.backend_for_arctic_team.service_interface;

import com.arctic.backend_for_arctic_team.dto.reponse.LoginResponse;
import com.arctic.backend_for_arctic_team.dto.reponse.RegisterResponse;
import com.arctic.backend_for_arctic_team.dto.request.LoginRequest;
import com.arctic.backend_for_arctic_team.dto.request.RegisterRequest;
import org.springframework.http.ResponseCookie;

public interface UserService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    ResponseCookie setRefreshTokenToCookie(String refreshToken);
}
