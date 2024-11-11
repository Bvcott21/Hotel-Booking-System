package com.bvcott.booking.dto.user;

import java.util.UUID;

import com.bvcott.booking.model.UserType;

import lombok.Data;
import lombok.experimental.SuperBuilder;



@Data @SuperBuilder
public class UserDTO {
    private UUID userId;
    private UserType userType;
    private String username;
    private String password;
}
