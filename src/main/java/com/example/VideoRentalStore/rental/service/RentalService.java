package com.example.VideoRentalStore.rental.service;

import com.example.VideoRentalStore.exceptionhandler.customexceptions.ResourceNotFoundException;
import com.example.VideoRentalStore.rental.dtos.RentalListDTO;
import com.example.VideoRentalStore.rental.dtos.UserRentalListDTO;
import com.example.VideoRentalStore.rental.model.Rental;
import com.example.VideoRentalStore.rental.model.RentalStatus;
import com.example.VideoRentalStore.rental.repo.RentalRepo;
import com.example.VideoRentalStore.user.model.User;
import com.example.VideoRentalStore.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepo rentalRepo;
    private final UserRepo userRepo;

    public RentalListDTO getAllRentals(RentalStatus status) {

//        if(status==null)
//            return GetAllRentalsDTO.toDTO(rentalRepo.findAll());
//        if(status.equals(RentalStatus.PENDING))
//                return GetAllRentalsDTO.toDTO(rentalRepo.findRentalByStatus(RentalStatus.PENDING));
//        if(status.equals(RentalStatus.LOSS))
//            return GetAllRentalsDTO.toDTO(rentalRepo.findRentalByStatus(RentalStatus.LOSS));
//        else
//            return GetAllRentalsDTO.toDTO(rentalRepo.findRentalByStatus(RentalStatus.RETURNED));


        List<Rental> rentals = rentalRepo.findAll();
        List<Rental> pendingRentals = new ArrayList<>();
        List<Rental> lossRentals = new ArrayList<>();
        List<Rental> returnedRentals = new ArrayList<>();
        if(rentals.isEmpty())
            return RentalListDTO.builder()
                    .rentals(Collections.emptyList())
                    .build();

        if(status == null)
            return RentalListDTO.toDTO(new ArrayList<>(rentals));

        if(status.equals(RentalStatus.PENDING))  {
            for(Rental rental : rentals) {
                if(rental.getStatus().equals(RentalStatus.PENDING))
                    pendingRentals.add(rental);
            }
        }
        if(status.equals(RentalStatus.RETURNED))  {
            for(Rental rental : rentals) {
                if(rental.getStatus().equals(RentalStatus.RETURNED))
                    returnedRentals.add(rental);
            }
        }
        else {
            for(Rental rental : rentals) {
                if(rental.getStatus().equals(RentalStatus.LOSS))
                    lossRentals.add(rental);
            }
        }


        List<Rental> finalFilteredList;

        if (status == RentalStatus.PENDING)
            finalFilteredList = pendingRentals;
         else if (status == RentalStatus.LOSS)
            finalFilteredList = lossRentals;
         else
            finalFilteredList = returnedRentals;

        return RentalListDTO.toDTO(finalFilteredList);

    }

    public UserRentalListDTO getUserRentals(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User not found!"));

        List<Rental> rentals = user.getRentals();
        return UserRentalListDTO.toDTO(user,rentals);

    }
}
