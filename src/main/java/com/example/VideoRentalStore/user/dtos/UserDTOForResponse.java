package com.example.VideoRentalStore.user.dtos;

import com.example.VideoRentalStore.user.model.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTOForResponse {

    private String userName;
    private Long contactNo;

    public static UserDTOForResponse toDTO(User user) {
        return UserDTOForResponse.builder()
                .userName(user.getUserName())
                .contactNo(user.getContactNo())
                .build();
    }

}
