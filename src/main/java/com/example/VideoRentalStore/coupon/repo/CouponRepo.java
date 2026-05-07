package com.example.VideoRentalStore.coupon.repo;

import com.example.VideoRentalStore.coupon.model.Coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface CouponRepo extends JpaRepository<Coupon, Long> {

    @Query("SELECT c FROM Coupon c WHERE c.couponCode =:couponCode")
    Optional<Coupon> findByCouponCode(@Param("couponCode")String couponCode);

}
