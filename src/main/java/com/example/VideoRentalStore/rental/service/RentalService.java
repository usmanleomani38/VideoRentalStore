package com.example.VideoRentalStore.rental.service;

import com.example.VideoRentalStore.apputils.CommonUtils;
import com.example.VideoRentalStore.exceptionhandler.customexceptions.ResourceNotFoundException;
import com.example.VideoRentalStore.rental.dto.response.RentalDTO;
import com.example.VideoRentalStore.rental.dtos.RentalListDTO;
import com.example.VideoRentalStore.rental.dtos.UserRentalListDTO;
import com.example.VideoRentalStore.rental.model.Rental;
import com.example.VideoRentalStore.rental.model.RentalStatus;
import com.example.VideoRentalStore.rental.repo.RentalRepo;
import com.example.VideoRentalStore.user.dtos.UserDTOForResponse;
import com.example.VideoRentalStore.user.model.User;
import com.example.VideoRentalStore.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepo rentalRepo;
    private final UserRepo userRepo;

    public RentalListDTO getAllRentals(Integer pageNumber,
                                       Integer pageSize,
                                       RentalStatus status,
                                       String sortBy,
                                       String sortOrder,
                                       Long rentalId,
                                       Long userId) {


        PageRequest pageRequest = PageRequest.of(
                pageNumber,
                pageSize,
                CommonUtils.buildSort(
                        sortBy,
                        sortOrder));

        if (userId != null && status != null) {
            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "User not found"
                    ));

            Page<Rental> page = rentalRepo.findByUserIdAndStatus(userId, status, pageRequest);
            var rentals = page.getContent();

            if (rentals.isEmpty())
                return RentalListDTO.builder()
                        .userRentals(UserRentalListDTO.builder()
                                .user(UserDTOForResponse.toDTO(user))
                                .rentals(List.of())
                                .build())
                        .totalElements(0L)
                        .build();

            return RentalListDTO.toDTO(
                    user,
                    rentals,
                    pageNumber,
                    pageSize,
                    page.getTotalElements(),
                    page.getTotalPages());
        }


        if (userId != null) {
            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "User not found"
                    ));

            Page<Rental> page = rentalRepo.findByUserId(userId, pageRequest);

            var rentals = page.getContent();

            if (rentals.isEmpty())
                return RentalListDTO.builder()
                        .userRentals(UserRentalListDTO.builder()
                                .user(UserDTOForResponse.toDTO(user))
                                .rentals(List.of())
                                .build())
                        .totalElements(0L)
                        .build();

            return RentalListDTO.toDTO(
                    user,
                    user.getRentals(),
                    pageNumber,
                    pageSize,
                    page.getTotalElements(),
                    page.getTotalPages());
        }

        if (rentalId != null) {
            Rental rental = rentalRepo.findById(rentalId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Rental not found!"
                    ));

            return RentalListDTO.toDTO(rental);
        }

//        if(status==null)
//            return GetAllRentalsDTO.toDTO(rentalRepo.findAll());
//        if(status.equals(RentalStatus.PENDING))
//                return GetAllRentalsDTO.toDTO(rentalRepo.findRentalByStatus(RentalStatus.PENDING));
//        if(status.equals(RentalStatus.LOSS))
//            return GetAllRentalsDTO.toDTO(rentalRepo.findRentalByStatus(RentalStatus.LOSS));
//        else
//            return GetAllRentalsDTO.toDTO(rentalRepo.findRentalByStatus(RentalStatus.RETURNED));


        if (status == null) {
            Page<Rental> page = rentalRepo.findAll(pageRequest);
            var rentals = page.getContent();

            if (rentals.isEmpty())
                return RentalListDTO.builder()
                        .rentals(Collections.emptyList())
                        .build();

            return RentalListDTO.toDTO(rentals,
                    pageNumber,
                    pageSize,
                    page.getTotalElements(),
                    page.getTotalPages());


        }
        else {

            Page<Rental> page = rentalRepo.findRentalByStatus(status, pageRequest);
            if (page.getContent().isEmpty())
                return RentalListDTO.builder()
                    .rentals(Collections.emptyList())
                    .build();

            return RentalListDTO.toDTO(page.getContent(),
                    pageNumber,
                    pageSize,
                    page.getTotalElements(),
                    page.getTotalPages());
        }
    }


    public UserRentalListDTO getUserRentals(Long userId, RentalStatus status) {

        User user = userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException(
                        "User not found!"
                ));

        if(status == null) {
            List<Rental> rentals = user.getRentals();
            if (rentals.isEmpty())
                return UserRentalListDTO.builder()
                        .rentals(Collections.emptyList())
                        .user(UserDTOForResponse.toDTO(user))
                        .build();
            return UserRentalListDTO.toDTO(user, rentals);
        }
        else
            return UserRentalListDTO.toDTO(user, userRepo.findByUserIdAndStatus(
                                                                            userId,
                                                                            status));
//        if(status.equals(RentalStatus.RETURNED))
//            return UserRentalListDTO.toDTO(user, userRepo.findByUserIdAndStatus(userId, status));
//        else if (status.equals(RentalStatus.LOSS))
//            return UserRentalListDTO.toDTO(user, userRepo.findByUserIdAndStatus(userId, status));
//        else
//            return UserRentalListDTO.toDTO(user, userRepo.findByUserIdAndStatus(userId, status));

    }

}
