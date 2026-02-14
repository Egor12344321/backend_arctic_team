package com.arctic.backend_for_arctic_team.expedition.exceptions;


import lombok.Getter;

@Getter
public class ParticipantException extends RuntimeException {
    private final ParticipantError error;

    public ParticipantException(ParticipantError error, String message) {
      super(message);
      this.error = error;
    }

    public enum ParticipantError {
      NOT_FOUND,
      ALREADY_EXISTS,
      INVALID_DATA,
      NOT_IN_EXPEDITION,
      ALREADY_IN_EXPEDITION,
      CANNOT_REMOVE_LEADER,
      MAX_PARTICIPANTS_REACHED
    }
}
