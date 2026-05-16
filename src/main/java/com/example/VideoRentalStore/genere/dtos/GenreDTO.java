package com.example.VideoRentalStore.genere.dtos;

import com.example.VideoRentalStore.genere.model.Genre;
import com.example.VideoRentalStore.movie.dtos.MovieDTO;
import com.example.VideoRentalStore.movie.model.Movie;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenreDTO {

    private Long genreId;
    @NotBlank(message = "Genre name must not be blank")
    @Size(min = 3, max = 50, message = "Genre name must be between 3 and 50 characters")
    private String genreName;
    private LocalDateTime createdAt;


    public static GenreDTO toDTO(Genre genre) {
        return GenreDTO.builder()
                .genreId(genre.getGenreId())
                .genreName(genre.getGenreName())
                .createdAt(genre.getCreatedAt())
                .build();
    }

}
