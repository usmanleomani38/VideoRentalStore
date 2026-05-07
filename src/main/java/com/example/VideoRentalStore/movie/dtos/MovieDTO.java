package com.example.VideoRentalStore.movie.dtos;

import com.example.VideoRentalStore.genere.model.Genre;
import com.example.VideoRentalStore.movie.model.Movie;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieDTO {

    private Long movieId;
    @NotBlank(message = "Movie name must not be blank")
    @Size(min = 3, max = 100, message = "Movie name must be between 2 and 100 characters")
    private String movieName;

    @Positive(message = "Duration must be a positive number")
    private Long duration;

    @Min(value = 1900, message = "Release year must be after 1900")
    private Integer releaseYear;

    @NotNull(message = "Available quantity is required")
    @Min(value = 0, message = "Available quantity cannot be negative")
    private Integer availableQuantity;

    @NotNull(message = "Daily rental rate is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Daily rental rate must be greater than 0")
    private Double dailyRentalRate;

    @NotEmpty(message = "At least one genre must be provided")
    private List<Long> genreIds;   //  for request
    private List<String> genreNames; // for response


    public static MovieDTO toDTO(Movie movie) {

        List<String> genreNames = new ArrayList<>();
        for(Genre genre : movie.getGenres())
            genreNames.add(genre.getGenreName());

        return MovieDTO.builder()
                .movieId(movie.getBarcode())
                .movieName(movie.getMovieName())
                .duration(movie.getDuration())
                .releaseYear(movie.getReleaseYear())
                .availableQuantity(movie.getAvailableQuantity())
                .dailyRentalRate(movie.getDailyRentalRate())
                .genreNames(genreNames)
                .build();
    }

}
