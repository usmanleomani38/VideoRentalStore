package com.example.VideoRentalStore.genere.dtos;

import com.example.VideoRentalStore.genere.model.Genre;
import com.example.VideoRentalStore.movie.model.Movie;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenreWithMoviesDTO {

    private String genreName;
    private List<MovieInfoDTO> movies;

    public static GenreWithMoviesDTO toDTO(Genre genre, List<Movie> movies) {

        List<MovieInfoDTO> movieDTOS = new ArrayList<>();
        for (Movie movie : movies)
            movieDTOS.add(MovieInfoDTO.toDTO(movie));

        return GenreWithMoviesDTO.builder()
                .movies(movieDTOS)
                .genreName(genre.getGenreName())
                .build();
    }
}
