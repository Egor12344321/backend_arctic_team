package com.arctic.backend_for_arctic_team.exceptions;


import com.arctic.backend_for_arctic_team.exceptions.custom_exceptions.InvalidCredentialsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
}
