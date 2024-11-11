package com.bvcott.booking.dto.user;

import java.util.UUID;

import com.bvcott.booking.model.UserType;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class UserCreateDTO {
    private UUID userId;
    private UserType userType;
    private Str