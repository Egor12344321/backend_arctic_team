package com.arctic.backend_for_arctic_team.auth.custom_exceptions;


public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {super(message);};
}
