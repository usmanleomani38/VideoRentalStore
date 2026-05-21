package com.example.VideoRentalStore.coupon.controller;

import com.example.VideoRentalStore.apputils.AppConstants;
import com.example.VideoRentalStore.coupon.dtos.CouponDTO;
import com.example.VideoRentalStore.coupon.dtos.CouponUpdateDTO;
import com.example.VideoRentalStore.coupon.dtos.CouponsDTO;
import com.example.VideoRentalStore.coupon.service.CouponService;
import com.example.VideoRentalStore.exceptionhandler.ApiResponse;
import com.example.VideoRentalStore.exceptionhandler.Status;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/add-coupon")
    public ResponseEntity<ApiResponse<CouponDTO>> addCoupon(
                                                    @Valid
                                                    @RequestBody
                                                    CouponDTO couponDTO) {

        ApiResponse<CouponDTO> response = ApiResponse.<CouponDTO>builder()
                .status(Status.CREATED)
                .message("Coupon added successfully")
                .data(couponService.addCoupon(couponDTO))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-coupon/{couponId}")
    public ResponseEntity<ApiResponse<CouponDTO>> getCouponById(
                                                    @PathVariable
                                                    Long couponId) {

        ApiResponse<CouponDTO> response = ApiResponse.<CouponDTO>builder()
                .status(Status.OK)
                .message("Coupon fetched successfully")
                .data(couponService.getCouponById(couponId))
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-coupon/{couponId}")
    public ResponseEntity<ApiResponse<String>> deleteCouponById(
                                                    @PathVariable
                                                    Long couponId) {

        couponService.deleteCouponById(couponId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(Status.OK)
                .message("Coupon deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-coupon/{couponId}")
    public ResponseEntity<ApiResponse<CouponUpdateDTO>> updateCouponById(
            @PathVariable
            Long couponId,
            @RequestBody
            CouponUpdateDTO couponUpdateDTO) {

        ApiResponse<CouponUpdateDTO> response = ApiResponse.<CouponUpdateDTO>builder()
                .status(Status.OK)
                .message("Coupon updated successfully")
                .data(couponService.updateCouponById(couponId, couponUpdateDTO))
                .build();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/get-coupons")
    public ResponseEntity<ApiResponse<CouponsDTO>>getAllCoupons(
            @RequestParam(name = "pageNumber",
                    defaultValue = AppConstants.PAGE_NUMBER,
                    required = false)
            Integer pageNumber,
            @RequestParam(name = "pageSize",
                    defaultValue = AppConstants.PAGE_SIZE,
                    required = false)
            Integer pageSize,
            @RequestParam(required = false)
            Boolean isActive,
            @RequestParam(name = "sortBy",
                    defaultValue = AppConstants.SORT_COUPONS_BY,
                    required = false)
            String sortBy,
            @RequestParam(name = "sortOrder",
                    defaultValue = AppConstants.SORT_DIR,
                    required = false)
            String sortOrder
    ) {

        CouponsDTO couponsDTO = couponService.getAllCoupons(pageNumber,
                                                                        pageSize,
                                                                        isActive,
                                                                        sortBy,
                                                                        sortOrder);
        boolean isEmpty = couponsDTO == null || couponsDTO.getCoupons().isEmpty();
        String message = isEmpty
                ? "No Records Found!"
                : "Coupons fetched Successfully ";

        ApiResponse<CouponsDTO> response = ApiResponse.<CouponsDTO>builder()
                .status(Status.OK)
                .message(message)
                .data(couponsDTO)
                .build();
        return ResponseEntity.ok(response);
    }
}
