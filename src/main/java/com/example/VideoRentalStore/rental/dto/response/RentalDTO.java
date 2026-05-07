package com.example.VideoRentalStore.rental.dto.response;

import com.example.VideoRentalStore.movie.model.Movie;
import com.example.VideoRentalStore.rental.model.Rental;
import com.example.VideoRentalStore.rental.model.RentalStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalDTO {

    private LocalDate rentalDate;
    private RentalStatus returnStatus;
    private String movieName;
    private Double dailyRentalRate;

    public static RentalDTO toDTO(Rental rental, Movie movie) {
        return RentalDTO.builder()
                .rentalDate(rental.getRentalDate())
                .returnStatus(rental.getStatus())
                .movieName(movie.getMovieName())
                .dailyRentalRate(movie.getDailyRentalRate())
                .build();
    }
}
