package com.arctic.backend_for_arctic_team.auth.custom_exceptions;

public class InvalidTokenRefreshException extends RuntimeException{
    public InvalidTokenRefreshException(String message){super(message);}
}
