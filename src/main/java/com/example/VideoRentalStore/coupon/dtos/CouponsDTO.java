package com.example.VideoRentalStore.coupon.dtos;

import com.example.VideoRentalStore.coupon.model.Coupon;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponsDTO {

    private List<CouponDTO> coupons;
    private int pageNumber;
    private int pageSize;
    private Long totalElements;
    private int totalPages;

    public static CouponsDTO toDTO(List<Coupon> couponsList, Integer pageNumber, Integer pageSize, int totalPages, Long totalElements) {

        List<CouponDTO> couponDTOS = new ArrayList<>();
        for(Coupon coupon : couponsList)
            couponDTOS.add(CouponDTO.toDTO(coupon));

        return CouponsDTO.builder()
                .coupons(couponDTOS)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }

}
