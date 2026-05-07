package com.example.VideoRentalStore.genere.dtos;


import com.example.VideoRentalStore.genere.model.Genre;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieCountDTO {

    private Long genreId;
    private String genreName;
    private Integer totalMovies;

    public static MovieCountDTO toDTO(Genre genre) {
        return MovieCountDTO.builder()
                .genreId(genre.getGenreId())
                .genreName(genre.getGenreName())
                .totalMovies(genre.getMovieList().size())
                .build();
    }

}
