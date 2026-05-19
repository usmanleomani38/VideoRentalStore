package com.example.VideoRentalStore.coupon.model;

import com.example.VideoRentalStore.audit.AuditFields;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Coupon extends AuditFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;
    private String couponCode;
    private Double discountPercent;
    private Boolean isActive;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime expiryDate;


}
