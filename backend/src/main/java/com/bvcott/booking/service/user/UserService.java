package com.bvcott.booking.service.user;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bvcott.booking.converter.user.UserConverter;
import com.bvcott.booking.dto.user.UserDTO;
import com.bvcott.booking.exception.user.UserNotFoundException;
import com.bvcott.booking.model.User;
import com.bvcott.booking.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service @AllArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final UserConverter userConverter;

    public List<UserDTO> findAll() {
        return userRepo
            .findAll()
            .stream()
            .map(user -> userConverter.toDto(user))
            .collect(Collectors.toList());     
    }

    public UserDTO findUserById(UUID id) {
        User user = userRepo
            .findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found with the provided id: " + id));
        return userConverter.toDto(user);
    }

}
