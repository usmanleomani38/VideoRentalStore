package com.example.VideoRentalStore.rental.controller;

import com.example.VideoRentalStore.apputils.AppConstants;
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
            RentalStatus status,
            @RequestParam(name = "sortBy",
                    defaultValue = AppConstants.SORT_RENTALS_BY,
                    required = false)
            String sortBy,
            @RequestParam(name = "sortOrder",
                    defaultValue = AppConstants.SORT_DIR,
                    required = false)
            String sortOrder
    ) {

        RentalListDTO rentalListDTO = rentalService.getAllRentals(status, sortBy, sortOrder);
        boolean isEmpty = rentalListDTO == null || rentalListDTO.getRentals().isEmpty();
        String message = isEmpty
                ?  "No Records Found!"
                : "Rentals fetched Successfully";
        ApiResponse<RentalListDTO> response = ApiResponse.<RentalListDTO>builder()
                .status(Status.OK)
                .message(message)
                .data(rentalListDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/rentals/user/{userId}")
    public ResponseEntity<ApiResponse<UserRentalListDTO>> getAllUserRentals(
            @PathVariable Long userId,
            @RequestParam(required = false) RentalStatus status) {

        ApiResponse<UserRentalListDTO> response = ApiResponse.<UserRentalListDTO>builder()
                .status(Status.OK)
                .message("Rental fetched Successfully")
                .data(rentalService.getUserRentals(userId, status))
                .build();
        return ResponseEntity.ok(response);
    }

}
