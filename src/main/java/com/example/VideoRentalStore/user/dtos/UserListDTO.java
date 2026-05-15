package com.example.VideoRentalStore.user.dtos;

import com.example.VideoRentalStore.user.model.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserListDTO {

    private List<UserDTO> users;

    public static UserListDTO toDTO(List<User> users) {

        List<UserDTO> userDTOS = new ArrayList<>();
        for(User user : users)
            userDTOS.add(UserDTO.toDTO(user));

        return UserListDTO.builder()
                .users(userDTOS)
                .build();

    }
}
