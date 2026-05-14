package com.example.VideoRentalStore.user.service;

import com.example.VideoRentalStore.apputils.CommonUtils;
import com.example.VideoRentalStore.exceptionhandler.customexceptions.ResourceNotFoundException;
import com.example.VideoRentalStore.user.dtos.UserDTO;
import com.example.VideoRentalStore.user.dtos.UsersDTO;
import com.example.VideoRentalStore.user.model.User;
import com.example.VideoRentalStore.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public UserDTO registerUser(UserDTO userDTO) {
            User newUser = new User();
            newUser.setUserName(userDTO.getUserName());
            newUser.setContactNo(userDTO.getContactNo());
            newUser.setAddress(userDTO.getAddress());
            newUser.setEmail(userDTO.getEmail());
            return UserDTO.toDTO(userRepo.save(newUser));
    }


    public UserDTO getUserByPhoneNo(Long phoneNo) {
         User user = userRepo.findByContactNo(phoneNo)
                 .orElseThrow(()-> new ResourceNotFoundException("User not found!"));
         return UserDTO.toDTO(user);
    }

    public String deleteUserByUserId(Long userId) {

         User user = userRepo.findById(userId)
                 .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
         if(!user.getRentals().isEmpty())
             throw new RuntimeException("User has rentals cannot delete!");
         userRepo.deleteById(userId);
         return "User deleted successfully";
    }

    public UserDTO updateUserByUserId(Long userId, UserDTO userDTO) {

        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found!"));
        user.setUserName(userDTO.getUserName());
        user.setContactNo(userDTO.getContactNo());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        return UserDTO.toDTO(userRepo.save(user));
    }

    public UsersDTO getAllUsers(String sortBy, String sortOrder) {

        List<User> users = userRepo.findAll(CommonUtils.buildSort(sortBy, sortOrder));
        if (users.isEmpty())
            return  UsersDTO.builder()
                    .users(Collections.emptyList())
                    .build();
        else
            return UsersDTO.toDTO(new ArrayList<>(users));
    }


    public UserDTO getUserByName(String userName) {

        if (!userName.matches("^[a-zA-Z ]*$"))
            throw new IllegalArgumentException("User Name contains only alphabets");

        User user = userRepo.findByUserNameContainingIgnoreCase(userName)
                .orElseThrow(()-> new ResourceNotFoundException("User not found!"));
      return UserDTO.toDTO(user);
    }

}
