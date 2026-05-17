package com.example.VideoRentalStore.user.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieUpdateDTO {

    private String movieName;
    private Long duration;
    private Integer releaseYear;
    private Integer availableQuantity;
    private Double dailyRentalRate;
    private List<String> genreNames; // for response
}
