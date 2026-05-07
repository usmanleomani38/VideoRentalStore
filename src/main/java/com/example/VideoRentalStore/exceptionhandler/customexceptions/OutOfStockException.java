package com.example.VideoRentalStore.exceptionhandler.customexceptions;

public class OutOfStockException extends RuntimeException{
    public OutOfStockException(String message) {
        super(message);
    }

}
