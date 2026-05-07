package com.example.VideoRentalStore.movie.dtos;


import com.example.VideoRentalStore.rental.model.Rental;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDTOForResponse {

    private String movieName;
    private String rentalStatus;
    private Long totalDays;
    private Double totalAmount;

    public static MovieDTOForResponse toDTO(Rental rental,
                                            Long totalDays)  {
        return MovieDTOForResponse.builder()
                .movieName(rental.getMovie().getMovieName())
                .rentalStatus(String.valueOf(rental.getStatus()))
                .totalDays(totalDays)
                .totalAmount(rental.getTotalAmount())
                .build();
    }
}
