package com.example.VideoRentalStore.user.dtos;


import com.example.VideoRentalStore.user.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

        private Long userId;
        @NotBlank(message = "Username cannot be empty")
        @Size(
                min = 3,
                max = 20,
                message = "Username must be between 3 and 20 characters"
        )
        @Pattern(
                regexp = "^[a-zA-Z ]*$",
                message = "Username must contain only letters and single spaces between words"
        )
        private String userName;
        @NotNull(message = "Contact number cannot be null")
        @Digits(
                integer = 15,
                fraction = 0,
                message = "Contact number must be numeric and up to 15 digits"
        )
        private Long contactNo;

        @Size(
                max = 100,
                message = "Address must not exceed 100 characters"
        )
        private String address;

        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Email must be valid")
        private String email;

        private List<UserDTO> users;


    public static UserDTO toDTO(User user) {

        return UserDTO.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .address(user.getAddress())
                .email(user.getEmail())
                .contactNo(user.getContactNo())
                .build();
    }

    public static UserDTO toDTO(List<User> userList) {

        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : userList)
            userDTOS.add(UserDTO.toDTO(user));

        return UserDTO.builder()
                .users(userDTOS)
                .build();

    }
}
