package com.example.VideoRentalStore.movie.dtos;

import com.example.VideoRentalStore.movie.model.Movie;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoviesDTO {

    private List<MovieDTO> movies;
    private int pageNumber;
    private int pageSize;
    private Long totalElements;
    private int totalPages;

    public static MoviesDTO toDTO(List<Movie> movies, Integer pageNumber, Integer pageSize, int totalPages, Long totalElements) {

        List<MovieDTO> movieDTOS = new ArrayList<>();

        for(Movie movie : movies)
            movieDTOS.add(MovieDTO.toDTO(movie));

        return MoviesDTO.builder()
                .movies(movieDTOS)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }
    public static MoviesDTO toDTO(List<Movie> movies) {

        List<MovieDTO> movieDTOS = new ArrayList<>();

        for(Movie movie : movies)
            movieDTOS.add(MovieDTO.toDTO(movie));

        return MoviesDTO.builder()
                .movies(movieDTOS)
                .build();
    }
}
