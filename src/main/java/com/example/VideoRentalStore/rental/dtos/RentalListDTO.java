package com.example.VideoRentalStore.rental.dtos;

import com.example.VideoRentalStore.rental.dto.response.RentalDTO;
import com.example.VideoRentalStore.rental.model.Rental;
import com.example.VideoRentalStore.user.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RentalListDTO {

    // this is field is for when list of rentals is fetched
    private List<RentalDetailsDTO> rentals;

    // this field is for when a single rental is fetched
    private RentalDTO rental;

    // this is for when user's rentals are fetched
    private UserRentalListDTO userRentals;

    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;

    public static RentalListDTO toDTO(List<Rental> rentals,
                                      Integer pageNumber,
                                      Integer pageSize,
                                      Long totalElements,
                                      Integer totalPages) {

        List<RentalDetailsDTO> rentalDetailsDTOS = new ArrayList<>();
        for(Rental rental : rentals)
            rentalDetailsDTOS.add(RentalDetailsDTO.toDTO(rental));

        return RentalListDTO.builder()
                .rentals(rentalDetailsDTOS)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }

    public static RentalListDTO toDTO(Rental rental) {

          return RentalListDTO.builder()
                  .rental(RentalDTO.toDTO(rental))
                  .build();
    }


    public static RentalListDTO toDTO(User user, List<Rental> rentals,
                                      Integer pageNumber,
                                      Integer pageSize,
                                      Long totalElements,
                                      Integer totalPages) {

        return RentalListDTO.builder()
                .userRentals(UserRentalListDTO.toDTO(user, rentals))
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }


}
