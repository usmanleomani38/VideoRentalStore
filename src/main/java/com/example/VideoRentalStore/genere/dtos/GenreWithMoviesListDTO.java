package com.example.VideoRentalStore.genere.dtos;


import com.example.VideoRentalStore.genere.model.Genre;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenreWithMoviesListDTO {

    private List<GenreWithMoviesDTO> genres;

    public static GenreWithMoviesListDTO toDTO(List<Genre> genres) {

        List<GenreWithMoviesDTO> genreWithMoviesDTOS = new ArrayList<>();
        for(Genre genre : genres)
            genreWithMoviesDTOS.add(GenreWithMoviesDTO.toDTO(genre, genre.getMovieList()));

        return GenreWithMoviesListDTO.builder()
                .genres(genreWithMoviesDTOS)
                .build();

    }


}
