package com.example.VideoRentalStore.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private Status status;
    private String message;
    private T data;

    public ApiResponse(Status status, String message) {
        this.status = status;
        this.message = message;
    }
    public ApiResponse(String message) {
        this.message = message;
    }
}
