package com.example.VideoRentalStore.movie.dtos;

import com.example.VideoRentalStore.genere.model.Genre;
import com.example.VideoRentalStore.movie.model.Movie;
import com.example.VideoRentalStore.user.dtos.OnCreate;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieDTO {

    private Long movieId;
    @NotBlank(message = "Movie name must not be blank")
    @Size(min = 3, max = 100, message = "Movie name must be between 3 and 100 characters")
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
    private Double discountedRate;
    private Double discount;

    @NotEmpty(groups = OnCreate.class, message = "At least one genre must be provided")
    private Set<Long> genreIds;   //  for request
    private List<String> genreNames; // for response


    public static MovieDTO toDTO(Movie movie) {


        double discount = 0;
        if (movie.getDiscountedRate() != null &&
                movie.getDailyRentalRate() != null &&
                movie.getDailyRentalRate() > 0) {

            discount =
                    ((movie.getDailyRentalRate() - movie.getDiscountedRate())
                            / movie.getDailyRentalRate()) * 100;
        }

        List<String> genreNames = new ArrayList<>();
        Set<Long> genreSet = new HashSet<>();
        for(Genre genre : movie.getGenres()) {
            genreNames.add(genre.getGenreName());
            genreSet.add(genre.getGenreId());
        }
        return MovieDTO.builder()
                .movieId(movie.getBarcode())
                .movieName(movie.getMovieName())
                .duration(movie.getDuration())
                .releaseYear(movie.getReleaseYear())
                .availableQuantity(movie.getAvailableQuantity())
                .dailyRentalRate(movie.getDailyRentalRate())
                .discountedRate(movie.getDiscountedRate())
                .discount(discount)
                .genreIds(genreSet)
                .genreNames(genreNames)
                .build();
    }

}
