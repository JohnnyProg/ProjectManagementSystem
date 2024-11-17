package com.grytaJan.ExpenseTracker.errors;

public class UserAlreadyExistsException extends Exception{

    public UserAlreadyExistsException(String email) {
        super("User with this email already exists: " + email);
    }
}
