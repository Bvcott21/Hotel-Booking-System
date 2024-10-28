package com.bvcott.booking.converter.user;

import org.springframework.stereotype.Component;

import com.bvcott.booking.dto.user.UserDTO;
import com.bvcott.booking.model.User;

@Component
public class UserConverter {
    public UserDTO toDto(User user) {
        return null;
    }

    public User toEntity(UserDTO userDTO) {
        return null;
    }
}
