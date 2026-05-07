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
public class MoviesCountDTO {

    private List<MovieCountDTO> genres;

    public static MoviesCountDTO toDTO(List<Genre> genreList) {

        List<MovieCountDTO> movieCountDTOS = new ArrayList<>();
        for (Genre genre : genreList)
            movieCountDTOS.add(MovieCountDTO.toDTO(genre));

       return MoviesCountDTO.builder()
               .genres(movieCountDTOS)
               .build();
    }

}
