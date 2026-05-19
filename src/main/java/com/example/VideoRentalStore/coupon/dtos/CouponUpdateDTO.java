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
public class CouponUpdateDTO {

    private Long couponId;
    private String couponCode;
    private Double discountPercent;
    private Boolean isActive;
    private LocalDate expiryDate;
    private LocalDateTime createdAt;


    public static CouponUpdateDTO toDTO(Coupon coupon) {

        return CouponUpdateDTO.builder()
                .couponId(coupon.getCouponId())
                .couponCode(coupon.getCouponCode())
                .discountPercent(coupon.getDiscountPercent())
                .expiryDate(coupon.getExpiryDate())
                .isActive(coupon.getIsActive())
                .createdAt(coupon.getCreatedAt())
                .build();
    }
}
