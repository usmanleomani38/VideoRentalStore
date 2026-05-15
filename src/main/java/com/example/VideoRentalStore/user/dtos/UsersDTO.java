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
    private int pageNumber;
    private int pageSize;
    private Long totalElements;
    private int totalPages;


    public static UsersDTO toDTO(List<User> userList, Integer pageNumber, Integer pageSize, int totalPages, Long totalElements) {

        List<UserDTO> userDTOS = new ArrayList<>();
        for(User user : userList)
            userDTOS.add(UserDTO.toDTO(user));

        return UsersDTO.builder()
                .users(userDTOS)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }
}
