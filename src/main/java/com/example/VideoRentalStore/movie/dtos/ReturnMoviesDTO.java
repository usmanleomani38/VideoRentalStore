package com.example.VideoRentalStore.movie.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnMoviesDTO {

    private List<ReturnItemDTO> movies;
    private String couponCode;

}
