package com.example.VideoRentalStore.exceptionhandler.customexceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
