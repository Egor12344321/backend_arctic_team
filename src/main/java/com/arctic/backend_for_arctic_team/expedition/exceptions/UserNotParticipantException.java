package com.arctic.backend_for_arctic_team.expedition.exceptions;

public class UserNotParticipantException extends RuntimeException {
    public UserNotParticipantException(String message) {
        super(message);
    }
}
