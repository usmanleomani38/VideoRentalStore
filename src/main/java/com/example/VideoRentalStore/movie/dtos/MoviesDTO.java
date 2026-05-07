package com.example.VideoRentalStore.movie.dtos;

import com.example.VideoRentalStore.movie.model.Movie;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoviesDTO {

    private List<MovieDTO> movies;

    public static MoviesDTO toDTO(List<Movie> movies) {

        List<MovieDTO> movieDTOS = new ArrayList<>();

        for(Movie movie : movies)
            movieDTOS.add(MovieDTO.toDTO(movie));

        return MoviesDTO.builder()
                .movies(movieDTOS)
                .build();
    }
}
