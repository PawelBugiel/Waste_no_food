package com.pawelbugiel.wastenofood.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super("User with email " + message + " already exists.");
    }
}
