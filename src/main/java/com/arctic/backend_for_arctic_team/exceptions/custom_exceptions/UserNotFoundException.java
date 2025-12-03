package com.arctic.backend_for_arctic_team.exceptions.custom_exceptions;


public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {super(message);};
}
