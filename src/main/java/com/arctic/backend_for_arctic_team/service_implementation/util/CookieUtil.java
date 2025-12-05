package com.arctic.backend_for_arctic_team.service_implementation.util;


import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
    public ResponseCookie setRefreshTokenToCookie(String refreshToken) {
        return ResponseCookie.from("refresh", refreshToken)
                .httpOnly(true)
                .maxAge(7 * 24 * 60 * 60)
                .path("/api/auth")
                .sameSite("Strict")
                .build();
    }

    public ResponseCookie deleteFromCookie(String name) {
        return ResponseCookie.from(name, "")
                .httpOnly(true)
                .secure(false) // true in production
                .maxAge(0)
                .path("/")
                .build();
    }
}
