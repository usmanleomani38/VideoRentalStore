package com.example.VideoRentalStore.rental.dtos;

import com.example.VideoRentalStore.rental.dto.response.RentalDTO;
import com.example.VideoRentalStore.rental.model.Rental;
import com.example.VideoRentalStore.user.dtos.UserDTOForResponse;
import com.example.VideoRentalStore.user.model.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRentalListDTO {

    private UserDTOForResponse user;
    private List<RentalDTO> rentals;

    public static UserRentalListDTO toDTO(User user, List<Rental> rentals) {

        List<RentalDTO> rentalDTOS = new ArrayList<>();
        for (Rental rental : rentals)
            rentalDTOS.add(RentalDTO.toDTO(rental, rental.getMovie()));

        return UserRentalListDTO.builder()
                .user(UserDTOForResponse.toDTO(user))
                .rentals(rentalDTOS)
                .build();
    }

}

