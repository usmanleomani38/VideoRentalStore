package com.example.VideoRentalStore.coupon.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponDashboardResponseDTO {

    private Long totalCoupons;
    private Long totalActiveCoupons;
    private Long totalDeActiveCoupons;
    private Long totalExpiredCoupons;
    private Long totalNonExpiredCoupons;

}
