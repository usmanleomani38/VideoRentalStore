package com.example.VideoRentalStore.movie.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoviesDashboard {

    private Long totalMovies;
    private Long totalAvailableStock;

}
