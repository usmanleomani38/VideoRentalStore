package com.example.VideoRentalStore.user.controller;


import com.example.VideoRentalStore.apputils.AppConstants;
import com.example.VideoRentalStore.exceptionhandler.ApiResponse;
import com.example.VideoRentalStore.exceptionhandler.Status;
import com.example.VideoRentalStore.user.dtos.UserDTO;
import com.example.VideoRentalStore.user.dtos.UsersDTO;
import com.example.VideoRentalStore.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/register-user")
    public ResponseEntity<ApiResponse<UserDTO>> registerUser(@Valid @RequestBody UserDTO registerUserDto) {
        ApiResponse<UserDTO> response = ApiResponse.<UserDTO>builder()
                .status(Status.CREATED)
                .message("User registered successfully")
                .data(userService.registerUser(registerUserDto))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-user/{phoneNo}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByPhoneNo(@PathVariable Long phoneNo) {
        ApiResponse<UserDTO> response = ApiResponse.<UserDTO>builder()
                .status(Status.OK)
                .message("User fetched successfully")
                .data(userService.getUserByPhoneNo(phoneNo))
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<ApiResponse<String>> deleteUserById(@PathVariable Long userId) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(Status.OK)
                .message("User deleted successfully")
                .data(userService.deleteUserByUserId(userId))
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-user/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUserById(@PathVariable Long userId,
                                                               @Valid @RequestBody UserDTO userDTO) {
        ApiResponse<UserDTO> response = ApiResponse.<UserDTO>builder()
                .status(Status.OK)
                .message("User updated successfully")
                .data(userService.updateUserByUserId(userId, userDTO))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all-user")
    public ResponseEntity<ApiResponse<UsersDTO>>getAllUsers(
            @RequestParam(name = "sortBy",
                    defaultValue = AppConstants.SORT_USERS_BY,
                    required = false) String sortBy,
            @RequestParam(name = "sortOrder",
                    defaultValue = AppConstants.SORT_DIR,
                    required = false) String sortOrder
    ) {

            ApiResponse<UsersDTO> response = ApiResponse.<UsersDTO>builder()
                    .status(Status.OK)
                    .message("Users fetched Successfully")
                    .data(userService.getAllUsers(sortBy, sortOrder))
                    .build();
            return ResponseEntity.ok(response);
    }


        @GetMapping("/get-user-by-name")
        public ResponseEntity<ApiResponse<UserDTO>>getUserByName(@RequestParam String userName) {
            float f = Float.parseFloat("3.124");
            ApiResponse<UserDTO> response = ApiResponse.<UserDTO>builder()
                    .status(Status.OK)
                    .message("User fetched Successfully")
                    .data(userService.getUserByName(userName))
                    .build();
            return ResponseEntity.ok(response);
        }

}
