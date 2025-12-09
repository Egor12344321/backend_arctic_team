package com.arctic.backend_for_arctic_team.auth.service_implementation.authantication.util;


import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
    public ResponseCookie setRefreshTokenToCookie(String refreshToken) {
        return ResponseCookie.from("refresh", refreshToken)
                .httpOnly(true)
                .secure(false)
                .maxAge(7 * 24 * 60 * 60)
                .path("/")
                .sameSite("Strict")
                .build();
    }

    public ResponseCookie deleteFromCookie(String name) {
        return ResponseCookie.from(name, "")
                .httpOnly(true)
                .secure(false)
                .maxAge(0)
                .path("/")
                .sameSite("Strict")
                .build();
    }
}
