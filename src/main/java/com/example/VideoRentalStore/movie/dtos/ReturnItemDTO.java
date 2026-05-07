package com.example.VideoRentalStore.movie.dtos;

import com.example.VideoRentalStore.rental.model.RentalStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnItemDTO {

      private Long movieId;
      private RentalStatus status;
}

