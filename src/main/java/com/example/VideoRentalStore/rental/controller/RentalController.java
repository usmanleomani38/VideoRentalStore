package com.example.VideoRentalStore.rental.controller;

import com.example.VideoRentalStore.exceptionhandler.ApiResponse;
import com.example.VideoRentalStore.exceptionhandler.Status;
import com.example.VideoRentalStore.rental.dtos.RentalListDTO;
import com.example.VideoRentalStore.rental.dtos.UserRentalListDTO;
import com.example.VideoRentalStore.rental.model.RentalStatus;
import com.example.VideoRentalStore.rental.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @GetMapping("/get-rentals")
    public ResponseEntity<ApiResponse<RentalListDTO>> getAllRentals(
            @RequestParam(required = false)
            RentalStatus status) {

        ApiResponse<RentalListDTO> response = ApiResponse.<RentalListDTO>builder()
                .status(Status.OK)
                .message("Rentals fetched Successfully")
                .data(rentalService.getAllRentals(status))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/rentals/user/{userId}")
    public ResponseEntity<ApiResponse<UserRentalListDTO>> getAllUserRentals(
            @PathVariable Long userId) {

        ApiResponse<UserRentalListDTO> response = ApiResponse.<UserRentalListDTO>builder()
                .status(Status.OK)
                .message("Rental fetched Successfully")
                .data(rentalService.getUserRentals(userId))
                .build();
        return ResponseEntity.ok(response);
    }
}
