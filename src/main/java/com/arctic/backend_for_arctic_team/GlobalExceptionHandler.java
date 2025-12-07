package com.arctic.backend_for_arctic_team;


import com.arctic.backend_for_arctic_team.auth.custom_exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({InvalidCredentialsException.class})
    public ResponseEntity<?> handleInvalidCredentialsException(InvalidCredentialsException e){
        log.error("Handle InvalidCredentialsException");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({RefreshNotFoundException.class, InvalidTokenRefreshException.class})
    public ResponseEntity<?> handleRefreshNotFoundException(RefreshNotFoundException e){
        log.error("Handle Token Exception");
        ResponseCookie deleteCookie = ResponseCookie.from("refresh", "")
                .httpOnly(true)
                .maxAge(0)
                .path("/")
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .body(e.getMessage());
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e){
        ResponseCookie deleteCookie = ResponseCookie.from("refresh", "")
                .httpOnly(true)
                .maxAge(0)
                .path("/")
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException e){
        ResponseCookie deleteCookie = ResponseCookie.from("refresh", "")
                .httpOnly(true)
                .maxAge(0)
                .path("/")
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .body(e.getMessage());
    }
}
