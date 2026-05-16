package com.example.VideoRentalStore.genere.dtos;

import com.example.VideoRentalStore.movie.model.Movie;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieInfoDTO {

    private String movieName;

    public static MovieInfoDTO toDTO(Movie movie) {

        return MovieInfoDTO.builder()
                .movieName(movie.getMovieName())
                .build();
    }
}
