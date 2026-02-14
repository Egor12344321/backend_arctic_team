package com.arctic.backend_for_arctic_team.expedition.exceptions;

public class ExpeditionException extends RuntimeException {
  private final ExpeditionException.ExpeditionError error;

  public ExpeditionException(ExpeditionException.ExpeditionError error, String message) {
    super(message);
    this.error = error;
  }

  public enum ExpeditionError {
    NOT_FOUND,
    ALREADY_EXISTS,
    INVALID_DATA,
    NOT_IN_EXPEDITION,
    ALREADY_IN_EXPEDITION,
    CANNOT_REMOVE_LEADER,
    MAX_PARTICIPANTS_REACHED
  }
}
