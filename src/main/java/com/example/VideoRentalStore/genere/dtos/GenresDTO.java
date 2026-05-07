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
public class GenresDTO {

    private List<GenreDTO> genres;


    public static GenresDTO toDTO(List<Genre> genreList) {

        List<GenreDTO> genreDTOS = new ArrayList<>();

        for(Genre genre : genreList)
            genreDTOS.add(GenreDTO.toDTO(genre));

        return GenresDTO.builder()
                .genres(genreDTOS)
                .build();

    }

}
