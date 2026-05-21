package com.example.VideoRentalStore.coupon.service;

import com.example.VideoRentalStore.apputils.CommonUtils;
import com.example.VideoRentalStore.coupon.dtos.CouponDTO;
import com.example.VideoRentalStore.coupon.dtos.CouponUpdateDTO;
import com.example.VideoRentalStore.coupon.dtos.CouponsDTO;
import com.example.VideoRentalStore.coupon.model.Coupon;
import com.example.VideoRentalStore.coupon.repo.CouponRepo;
import com.example.VideoRentalStore.exceptionhandler.customexceptions.ResourceNotFoundException;
import com.example.VideoRentalStore.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepo couponRepo;

    public CouponDTO addCoupon(CouponDTO couponDTO) {

        couponRepo.findByCouponCode(couponDTO.getCouponCode())
                .ifPresent(c-> {
                    throw new IllegalStateException(
                            "This Coupon Code already exists!"
                    );
                });

        Coupon coupon = new Coupon();
        coupon.setCouponCode(couponDTO.getCouponCode().toUpperCase());
        coupon.setDiscountPercent(couponDTO.getDiscountPercent());
        coupon.setExpiryDate(couponDTO.getExpiryDate());
//        if(coupon.getExpiryDate().isBefore(LocalDateTime.now()))
//            coupon.setIsActive(false);
        coupon.setIsActive(couponDTO.getIsActive());

        return CouponDTO.toDTO(couponRepo.save(coupon));
    }


    public void deleteCouponById(Long couponId) {

        if (!couponRepo.existsById(couponId))
            throw new ResourceNotFoundException(
                    "Coupon not exists!"
            );
        couponRepo.deleteById(couponId);
    }

    @Transactional
    public CouponUpdateDTO updateCouponById(Long couponId, CouponUpdateDTO couponUpdateDTO) {

        Coupon coupon = couponRepo.findById(couponId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Coupon not found!"
                ));

        couponRepo.findByCouponCode(couponUpdateDTO.getCouponCode())
                .ifPresent(c-> {
                    if(!coupon.getCouponId().equals(couponId))
                        throw new IllegalStateException(
                                "This Coupon Code already exists!"
                        );
                });

        if (couponUpdateDTO.getCouponCode() != null) {
            coupon.setCouponCode(
                    couponUpdateDTO.getCouponCode().toUpperCase()
            );
        }

        if (couponUpdateDTO.getDiscountPercent() != null)
            coupon.setDiscountPercent(couponUpdateDTO.getDiscountPercent());

        if (couponUpdateDTO.getIsActive() != null)
            coupon.setIsActive(couponUpdateDTO.getIsActive());

        if (couponUpdateDTO.getExpiryDate() != null)
            coupon.setExpiryDate(couponUpdateDTO.getExpiryDate());

        return CouponUpdateDTO.toDTO(couponRepo.save(coupon));
    }

    public CouponDTO getCouponById(Long couponId) {

        Coupon coupon = couponRepo.findById(couponId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Coupon not found!"
                ));
        return CouponDTO.toDTO(coupon);

    }

    public CouponsDTO getAllCoupons(Integer pageNumber, Integer pageSize, Boolean isActive, String sortBy, String sortOrder ) {

        PageRequest pageRequest = PageRequest.of(pageNumber,pageSize,CommonUtils.buildSort(sortBy, sortOrder));
        Page<Coupon> page = couponRepo.findAll(pageRequest);
        var coupons = page.getContent();
        var totalPages = page.getTotalPages();
        var totalElements = page.getTotalElements();

        List<Coupon> activeCoupons = new ArrayList<>();
        List<Coupon> deActiveCoupons = new ArrayList<>();
        if (coupons.isEmpty())
            return CouponsDTO.builder()
                    .coupons(Collections.emptyList())
                    .build();

        if(isActive == null)
            return CouponsDTO.toDTO(new ArrayList<>(coupons), pageNumber,pageSize, totalPages, totalElements);

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
         ? CouponsDTO.toDTO(activeCoupons, pageNumber,pageSize, totalPages, totalElements) :
         CouponsDTO.toDTO(deActiveCoupons, pageNumber,pageSize, totalPages, totalElements);
    }
}
