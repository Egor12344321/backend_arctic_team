package com.arctic.backend_for_arctic_team.expedition.exceptions;

import com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation.exceptions.GigaChatClientException;
import com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation.exceptions.MetricsForAnalyticsNotFountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExpeditionExceptionHandler {

    @ExceptionHandler({ExpeditionNotFoundException.class, ParticipantNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(Exception e){
        log.error("Handle not found exception: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({UserNotParticipantException.class})
    public ResponseEntity<?> handleUserException(Exception e){
        log.error("Handle user not participant exception: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({EditExpeditionException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleEditExpeditionException(Exception e){
        log.error("Handle edit expedition exception: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({ParticipantException.class})
    public ResponseEntity<?> handleParticipantException(ParticipantException e){
        log.error("Handle participant exception: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getError());
    }
    
    @ExceptionHandler({PythonClientException.class, GigaChatClientException.class, MetricsForAnalyticsNotFountException.class})
    public ResponseEntity<?> handlePythonClientException(PythonClientException e){
        return ResponseEntity.status(e.getErrorCode().value()).body(e.getMessage());
    }

}
