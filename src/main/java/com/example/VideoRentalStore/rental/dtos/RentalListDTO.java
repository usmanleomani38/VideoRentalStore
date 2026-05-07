package com.example.VideoRentalStore.rental.dtos;

import com.example.VideoRentalStore.rental.dto.response.RentalDTO;
import com.example.VideoRentalStore.rental.model.Rental;
import com.example.VideoRentalStore.user.dtos.UserDTO;
import com.example.VideoRentalStore.user.model.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalListDTO {

    private List<RentalDetailsDTO> rentals;

    public static RentalListDTO toDTO(List<Rental> rentals) {
        List<RentalDetailsDTO> rentalDetailsDTOS = new ArrayList<>();
        for(Rental rental : rentals)
            rentalDetailsDTOS.add(RentalDetailsDTO.toDTO(rental));

        return RentalListDTO.builder()
                .rentals(rentalDetailsDTOS)
                .build();
    }

}
