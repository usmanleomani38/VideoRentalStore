package com.example.VideoRentalStore.rental.dtos;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalDashboardResponse {

    private Long totalRentals;
    private Long pendingCount;
    private Long returnedCount;
    private Long lossCount;
}
