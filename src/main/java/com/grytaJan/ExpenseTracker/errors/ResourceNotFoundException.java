package com.grytaJan.ExpenseTracker.errors;

public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException(String resourceName, String fieldName) {
        super(String.format("%s not found with the given input: %s", resourceName, fieldName));
    }
}
