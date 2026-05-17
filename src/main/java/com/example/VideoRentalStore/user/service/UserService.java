package com.example.VideoRentalStore.user.service;

import com.example.VideoRentalStore.apputils.CommonUtils;
import com.example.VideoRentalStore.exceptionhandler.customexceptions.ResourceNotFoundException;
import com.example.VideoRentalStore.user.dtos.UserDTO;
import com.example.VideoRentalStore.user.dtos.UsersDTO;
import com.example.VideoRentalStore.user.dtos.UserListDTO;
import com.example.VideoRentalStore.user.model.User;
import com.example.VideoRentalStore.user.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.WordUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            newUser.setUserName(WordUtils.capitalize(userDTO.getUserName()));
            newUser.setContactNo(userDTO.getContactNo());
            newUser.setAddress(WordUtils.capitalize(userDTO.getAddress()));
            newUser.setEmail(userDTO.getEmail());
            return UserDTO.toDTO(userRepo.save(newUser));
    }


    public UserDTO getUserByPhoneNo(Long phoneNo) {
         User user = userRepo.findByContactNo(phoneNo)
                 .orElseThrow(()-> new ResourceNotFoundException("User not found!"));
         return UserDTO.toDTO(user);
    }

    @Transactional
    public String deleteUserByUserId(Long userId) {

         User user = userRepo.findById(userId)
                 .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
         if(!user.getRentals().isEmpty())
             throw new RuntimeException("User has rentals cannot delete!");
         userRepo.delete(user);
         return "User deleted successfully";
    }

    @Transactional
    public UserDTO updateUserByUserId(Long userId, UserDTO userDTO) {

        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found!"));
        user.setUserName(userDTO.getUserName());
        user.setContactNo(userDTO.getContactNo());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        return UserDTO.toDTO(userRepo.save(user));
    }

    public UsersDTO getAllUsers(String sortBy, String sortOrder, Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = PageRequest.of(pageNumber,pageSize,CommonUtils.buildSort(sortBy, sortOrder));
        Page<User> page = userRepo.findAll(pageRequest);
        var users = page.getContent();
        var totalPages = page.getTotalPages();
        var totalElements = page.getTotalElements();

        if (users.isEmpty())
            return  UsersDTO.builder()
                    .users(Collections.emptyList())
                    .build();
        return UsersDTO.toDTO(users,pageNumber,pageSize, totalPages, totalElements);
    }


    public UserListDTO getUserByName(String userName) {

        if (!userName.matches("^[a-zA-Z ]*$"))
            throw new IllegalArgumentException("User Name contains only alphabets");

        List<User> users = userRepo.findByUserNameStartsWithIgnoreCase(userName);
        if(users.isEmpty())
            throw new ResourceNotFoundException("Users not found!");
        return UserListDTO.toDTO(users);
    }

}
