package com.arctic.backend_for_arctic_team.service_interface;

import com.arctic.backend_for_arctic_team.dto.reponse.auth_responses.LoginResponse;
import com.arctic.backend_for_arctic_team.dto.reponse.auth_responses.RegisterResponse;
import com.arctic.backend_for_arctic_team.dto.reponse.auth_responses.UpdateTokensResponse;
import com.arctic.backend_for_arctic_team.dto.request.auth_requests.LoginRequest;
import com.arctic.backend_for_arctic_team.dto.request.auth_requests.RegisterRequest;
import org.springframework.http.ResponseCookie;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    UpdateTokensResponse refresh(String refreshToken);
    void logout(String accessToken);
}
