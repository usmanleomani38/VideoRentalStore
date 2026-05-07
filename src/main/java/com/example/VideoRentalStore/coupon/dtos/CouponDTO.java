package com.example.VideoRentalStore.coupon.dtos;

import com.example.VideoRentalStore.coupon.model.Coupon;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponDTO {

    private Long couponId;
    @NotBlank(message = "Coupon code must not be blank")
    @Size(min = 5, max = 20, message = "Coupon code must be between 5 and 20 characters")
    private String couponCode;

    @NotNull(message = "Discount percent is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Discount must be greater than 0")
    @DecimalMax(value = "100.0", message = "Discount cannot exceed 100%")
    private Double discountPercent;

    @NotNull(message = "Active status must be specified")
    private Boolean isActive;

    @NotNull(message = "Expiry date is required")
    @Future(message = "Expiry date must be in the future")
    private LocalDate expiryDate;
    private LocalDateTime createdAt;

    public static CouponDTO toDTO(Coupon coupon) {

        return CouponDTO.builder()
                .couponId(coupon.getCouponId())
                .couponCode(coupon.getCouponCode())
                .discountPercent(coupon.getDiscountPercent())
                .expiryDate(coupon.getExpiryDate())
                .isActive(coupon.getIsActive())
                .createdAt(coupon.getCreatedAt())
                .build();
    }
}
