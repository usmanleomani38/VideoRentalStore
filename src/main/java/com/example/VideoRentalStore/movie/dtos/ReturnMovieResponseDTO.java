package com.example.VideoRentalStore.movie.dtos;

import com.example.VideoRentalStore.rental.model.Rental;
import com.example.VideoRentalStore.user.dtos.UserDTOForResponse;
import com.example.VideoRentalStore.user.model.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnMovieResponseDTO {

    private UserDTOForResponse user;
    private List<MovieDTOForResponse> movies;
    private Double grandTotal;


    public static ReturnMovieResponseDTO toDTO(List<Rental> rentals,
                                               Long totalDays,
                                               User user,
                                               Double totalAmount) {

        List<MovieDTOForResponse> movieDTOForResponseDTO = new ArrayList<>();
        for(Rental rental : rentals)
            movieDTOForResponseDTO.add(MovieDTOForResponse.toDTO(rental, totalDays));


        return ReturnMovieResponseDTO.builder()
                .user(UserDTOForResponse.toDTO(user))
                .movies(movieDTOForResponseDTO)
                .grandTotal(totalAmount)
                .build();

    }
}
