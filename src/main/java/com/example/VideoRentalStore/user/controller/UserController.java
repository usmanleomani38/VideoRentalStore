package com.example.VideoRentalStore.user.controller;


import com.example.VideoRentalStore.apputils.AppConstants;
import com.example.VideoRentalStore.exceptionhandler.ApiResponse;
import com.example.VideoRentalStore.exceptionhandler.Status;
import com.example.VideoRentalStore.user.dtos.UserDTO;
import com.example.VideoRentalStore.user.dtos.UserListDTO;
import com.example.VideoRentalStore.user.dtos.UsersDTO;
import com.example.VideoRentalStore.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping("/register-user")
    public ResponseEntity<ApiResponse<UserDTO>> registerUser(
                                                    @Valid
                                                    @RequestBody
                                                    UserDTO registerUserDto) {

        ApiResponse<UserDTO> response = ApiResponse.<UserDTO>builder()
                .status(Status.CREATED)
                .message("User registered successfully")
                .data(userService.registerUser(registerUserDto))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-user/{phoneNo}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByPhoneNo(@PathVariable
                                                                     @Pattern(regexp = "^[0-9]+$", message = "Only digits allowed")
                                                                     String phoneNo) {

        ApiResponse<UserDTO> response = ApiResponse.<UserDTO>builder()
                .status(Status.OK)
                .message("User fetched successfully")
                .data(userService.getUserByPhoneNo(phoneNo))
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<ApiResponse<String>> deleteUserById(
                                                        @PathVariable
                                                        Long userId) {

        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(Status.OK)
                .message("User deleted successfully")
                .data(userService.deleteUserByUserId(userId))
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-user/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUserById(@PathVariable
                                                                   Long userId,
                                                               @Valid
                                                               @RequestBody
                                                               UserDTO userDTO) {

        ApiResponse<UserDTO> response = ApiResponse.<UserDTO>builder()
                .status(Status.OK)
                .message("User updated successfully")
                .data(userService.updateUserByUserId(userId, userDTO))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all-user")
    public ResponseEntity<ApiResponse<UsersDTO>> getAllUsers(
            @RequestParam(name = "pageNumber",
                    defaultValue = AppConstants.PAGE_NUMBER,
                    required = false)
            Integer pageNumber,
            @RequestParam(name = "pageSize",
                    defaultValue = AppConstants.PAGE_SIZE,
                    required = false)
            Integer pageSize,
            @RequestParam(name = "sortBy",
                    defaultValue = AppConstants.SORT_USERS_BY,
                    required = false)
            String sortBy,
            @RequestParam(name = "sortOrder",
                    defaultValue = AppConstants.SORT_DIR,
                    required = false)
            String sortOrder
    ) {

        UsersDTO usersDTO = userService.getAllUsers(sortBy, sortOrder,pageNumber, pageSize);
        boolean isEmpty = usersDTO == null || usersDTO.getUsers().isEmpty();
        String message = isEmpty
                ? "No Records Found!"
                : "Users fetched Successfully ";

            ApiResponse<UsersDTO> response = ApiResponse.<UsersDTO>builder()
                    .status(Status.OK)
                    .message(message)
                    .data(usersDTO)
                    .build();
            return ResponseEntity.ok(response);
    }


        @GetMapping("/get-user-by-name")
        public ResponseEntity<ApiResponse<UserListDTO>>getUserByName(
                                                            @RequestParam
                                                            String userName) {
            float f = Float.parseFloat("3.124");
            ApiResponse<UserListDTO> response = ApiResponse.<UserListDTO>builder()
                    .status(Status.OK)
                    .message("User fetched Successfully")
                    .data(userService.getUserByName(userName))
                    .build();
            return ResponseEntity.ok(response);
        }

}
