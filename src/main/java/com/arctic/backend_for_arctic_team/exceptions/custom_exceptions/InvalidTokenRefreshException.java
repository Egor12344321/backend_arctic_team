package com.arctic.backend_for_arctic_team.exceptions.custom_exceptions;

public class InvalidTokenRefreshException extends RuntimeException{
    public InvalidTokenRefreshException(String message){super(message);}
}
