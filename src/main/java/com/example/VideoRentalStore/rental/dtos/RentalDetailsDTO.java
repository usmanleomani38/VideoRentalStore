package com.example.VideoRentalStore.rental.dtos;

import com.example.VideoRentalStore.rental.model.Rental;
import com.example.VideoRentalStore.rental.model.RentalStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalDetailsDTO {

    private Long rentalId;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private RentalStatus status;
    private String userName;
    private String movieName;
    private Double totalAmount;

    public static RentalDetailsDTO toDTO(Rental rental) {

        return RentalDetailsDTO.builder()
                .rentalId(rental.getRentalId())
                .rentalDate(rental.getRentalDate())
                .returnDate(rental.getReturnDate())
                .status(rental.getStatus())
                .totalAmount(rental.getTotalAmount())
                .userName(rental.getUser().getUserName())
                .movieName(rental.getMovie().getMovieName())
                .build();
    }

}
