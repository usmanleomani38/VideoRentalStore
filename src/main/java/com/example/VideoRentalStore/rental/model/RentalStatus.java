package com.example.VideoRentalStore.rental.model;


public enum RentalStatus {

    PENDING,   // when user has taken movie but not returned yet
    RETURNED,  // when movie is returned
    OVERDUE,   // when return date is crossed
    CANCELLED,  // if rental was cancelled
    LOSS
}
