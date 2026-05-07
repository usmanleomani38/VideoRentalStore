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
public class UsersDTO {

    private List<UserDTO> users;

    public static UsersDTO toDTO(List<User> userList) {

        List<UserDTO> userDTOS = new ArrayList<>();
        for(User user : userList)
            userDTOS.add(UserDTO.toDTO(user));

        return UsersDTO.builder()
                .users(userDTOS)
                .build();
    }
}
