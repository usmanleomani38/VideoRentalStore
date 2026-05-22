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

    private int pageNumber;
    private int pageSize;
    private Long totalElements;
    private int totalPages;


    public static GenresDTO toDTO(List<Genre> genreList,
                                  Integer pageNumber,
                                  Integer pageSize,
                                  int totalPages,
                                  Long totalElements) {

        List<GenreDTO> genreDTOS = new ArrayList<>();

        for(Genre genre : genreList)
            genreDTOS.add(GenreDTO.toDTO(genre));

        return GenresDTO.builder()
                .genres(genreDTOS)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();

    }

}
