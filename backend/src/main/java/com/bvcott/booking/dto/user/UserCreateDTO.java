package com.bvcott.booking.dto.user;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class UserCreateDTO {
    private UUID userId;
    private String userType;
    private String username;
    private String password;
}
