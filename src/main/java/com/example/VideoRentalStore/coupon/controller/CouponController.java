package com.example.VideoRentalStore.coupon.controller;

import com.example.VideoRentalStore.apputils.AppConstants;
import com.example.VideoRentalStore.coupon.dtos.CouponDTO;
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
    public ResponseEntity<ApiResponse<CouponDTO>> addCoupon(@Valid @RequestBody CouponDTO couponDTO) {
        ApiResponse<CouponDTO> response = ApiResponse.<CouponDTO>builder()
                .status(Status.CREATED)
                .message("Coupon added successfully")
                .data(couponService.addCoupon(couponDTO))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-coupon/{couponId}")
    public ResponseEntity<ApiResponse<CouponDTO>> getCouponById(@PathVariable Long couponId) {
        ApiResponse<CouponDTO> response = ApiResponse.<CouponDTO>builder()
                .status(Status.OK)
                .message("Coupon fetched successfully")
                .data(couponService.getCouponById(couponId))
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-coupon/{couponId}")
    public ResponseEntity<ApiResponse<String>> deleteCouponById(@PathVariable Long couponId) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(Status.OK)
                .message("Coupon deleted successfully")
                .data(couponService.deleteCouponById(couponId))
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-coupon/{couponId}")
    public ResponseEntity<ApiResponse<CouponDTO>> updateCouponById(@PathVariable Long couponId, @RequestBody CouponDTO couponDTO) {
        ApiResponse<CouponDTO> response = ApiResponse.<CouponDTO>builder()
                .status(Status.OK)
                .message("Coupon updated successfully")
                .data(couponService.updateCouponById(couponId, couponDTO))
                .build();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/get-coupons")
    public ResponseEntity<ApiResponse<CouponsDTO>>getAllCoupons(
            @RequestParam(name = "pageNumber",
                    defaultValue = AppConstants.PAGE_NUMBER,
                    required = false) Integer pageNumber,
            @RequestParam(name = "pageSize",
                    defaultValue = AppConstants.PAGE_SIZE,
                    required = false) Integer pageSize,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(name = "sortBy",
                    defaultValue = AppConstants.SORT_COUPONS_BY,
                    required = false) String sortBy,
            @RequestParam(name = "sortOrder",
                    defaultValue = AppConstants.SORT_DIR,
                    required = false) String sortOrder
    ) {

        ApiResponse<CouponsDTO> response = ApiResponse.<CouponsDTO>builder()
                .status(Status.OK)
                .message("Coupons fetched Successfully")
                .data(couponService.getAllCoupons(pageNumber, pageSize, isActive, sortBy, sortOrder))
                .build();
        return ResponseEntity.ok(response);
    }
}
