package com.example.VideoRentalStore.rental.controller;

import com.example.VideoRentalStore.apputils.AppConstants;
import com.example.VideoRentalStore.exceptionhandler.ApiResponse;
import com.example.VideoRentalStore.exceptionhandler.Status;
import com.example.VideoRentalStore.movie.dtos.MovieDTO;
import com.example.VideoRentalStore.rental.dtos.RentalDashboardResponse;
import com.example.VideoRentalStore.rental.dtos.RentalListDTO;
import com.example.VideoRentalStore.rental.model.RentalStatus;
import com.example.VideoRentalStore.rental.service.RentalService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @GetMapping("/get-rentals")
    public ResponseEntity<ApiResponse<RentalListDTO>> getAllRentals(
            @RequestParam(name = "pageNumber",
                    defaultValue = AppConstants.PAGE_NUMBER,
                    required = false)
            Integer pageNumber,
            @RequestParam(name = "pageSize",
                    defaultValue = AppConstants.PAGE_SIZE,
                    required = false)
            Integer pageSize,
            @RequestParam(name = "sortBy",
                    defaultValue = AppConstants.SORT_RENTALS_BY,
                    required = false)
            String sortBy,
            @RequestParam(name = "sortOrder",
                    defaultValue = AppConstants.SORT_DIR,
                    required = false)
            String sortOrder,
            @RequestParam(
                    required = false)
            Long rentalId,
            @RequestParam(
                    required = false)
            Long userId,
            @RequestParam(required = false)
            RentalStatus status) {

        if (rentalId != null && userId != null)
            throw new IllegalArgumentException(
                    "Cannot filter by both rentalId and userId simultaneously"
            );

        if (rentalId != null && status != null)
            throw new IllegalArgumentException(
                    "Cannot filter by both rentalId and status simultaneously"
            );

        RentalListDTO rentalListDTO = rentalService.getAllRentals(pageNumber,
                                                                     pageSize,
                                                                     status,
                                                                     sortBy,
                                                                     sortOrder,
                                                                     rentalId,
                                                                     userId);
            boolean isEmpty;

            if (rentalListDTO.getRental() != null)
                isEmpty = false;
             else if (rentalListDTO.getUserRentals() != null)
                isEmpty = rentalListDTO.getUserRentals().getRentals() == null
                        || rentalListDTO.getUserRentals().getRentals().isEmpty();
             else
                isEmpty = rentalListDTO.getRentals() == null
                        || rentalListDTO.getRentals().isEmpty();

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


    @PostMapping("/show-revenue")
    public ResponseEntity<ApiResponse<Double>>getTotalRevenue(
            @RequestParam(
                    required = false)
            String movieName,
            @RequestParam(
                    required = false)
            LocalDate startDate,
            @RequestParam(
                    required = false)
            LocalDate endDate,
            @RequestParam(
                    required = false)
            RentalStatus status

    ) {

        ApiResponse<Double> response = ApiResponse.<Double>builder()
                .status(Status.OK)
                .message("Record fetched Successfully")
                .data(rentalService.getTotalRevenue(movieName, startDate, endDate, status))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-total-rentals")
    public ResponseEntity<ApiResponse<RentalDashboardResponse>>getRentalsCount() {

        ApiResponse<RentalDashboardResponse> response = ApiResponse.<RentalDashboardResponse>builder()
                .status(Status.SUCCESS)
                .message("Record fetched Successfully")
                .data(rentalService.getRentalsCount())
                .build();
        return ResponseEntity.ok(response);
    }


}
