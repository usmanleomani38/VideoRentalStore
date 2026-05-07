package com.example.VideoRentalStore.coupon.service;

import com.example.VideoRentalStore.coupon.dtos.CouponDTO;
import com.example.VideoRentalStore.coupon.dtos.CouponsDTO;
import com.example.VideoRentalStore.coupon.model.Coupon;
import com.example.VideoRentalStore.coupon.repo.CouponRepo;
import com.example.VideoRentalStore.exceptionhandler.customexceptions.ResourceNotFoundException;
import com.example.VideoRentalStore.user.dtos.UsersDTO;
import com.example.VideoRentalStore.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepo couponRepo;

    public CouponDTO addCoupon(CouponDTO couponDTO) {

        Coupon coupon = new Coupon();
        coupon.setCouponCode(couponDTO.getCouponCode());
        coupon.setDiscountPercent(couponDTO.getDiscountPercent());
        coupon.setIsActive(couponDTO.getIsActive());
        coupon.setExpiryDate(couponDTO.getExpiryDate());
        return CouponDTO.toDTO(couponRepo.save(coupon));
    }

    public String deleteCouponById(Long couponId) {

        if (!couponRepo.existsById(couponId))
            throw new ResourceNotFoundException("Coupon not exists!");
        couponRepo.deleteById(couponId);
        return "Coupon Deleted!";
    }

    public CouponDTO updateCouponById(Long couponId, CouponDTO couponDTO) {

        Coupon coupon = couponRepo.findById(couponId)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found!"));
        coupon.setCouponCode(couponDTO.getCouponCode());
        coupon.setDiscountPercent(couponDTO.getDiscountPercent());
        coupon.setIsActive(couponDTO.getIsActive());
        coupon.setExpiryDate(couponDTO.getExpiryDate());
        return CouponDTO.toDTO(couponRepo.save(coupon));

    }

    public CouponDTO getCouponById(Long couponId) {

        Coupon coupon = couponRepo.findById(couponId)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found!"));
        return CouponDTO.toDTO(coupon);

    }

    public CouponsDTO getAllCoupons(Boolean isActive) {

        List<Coupon> coupons = couponRepo.findAll();
        List<Coupon> activeCoupons = new ArrayList<>();
        List<Coupon> deActiveCoupons = new ArrayList<>();
        if (coupons.isEmpty())
            return CouponsDTO.builder()
                    .coupons(Collections.emptyList())
                    .build();

        if(isActive == null)
            return CouponsDTO.toDTO(new ArrayList<>(coupons));

        if (isActive) {
            for (Coupon coupon : coupons) {
                if (coupon.getIsActive() == true)
                    activeCoupons.add(coupon);

            }
        }
        else {
            for (Coupon coupon : coupons) {
                if (coupon.getIsActive() == false)
                    deActiveCoupons.add(coupon);
            }
        }

       return !activeCoupons.isEmpty()
         ? CouponsDTO.toDTO(new ArrayList<>(activeCoupons)) :
         CouponsDTO.toDTO(new ArrayList<>(deActiveCoupons));
    }
}
