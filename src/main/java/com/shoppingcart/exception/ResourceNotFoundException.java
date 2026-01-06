package com.shoppingcart.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super(resourceName + " not found with " + field + ": " + fieldId);
    }

    public ResourceNotFoundException(String resourceName, String field, String fieldName) {
        super(resourceName + " not found with " + field + ": " + fieldName);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}