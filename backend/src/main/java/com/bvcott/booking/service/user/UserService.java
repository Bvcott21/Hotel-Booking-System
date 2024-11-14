package com.bvcott.booking.service.user;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bvcott.booking.converter.user.UserConverter;
import com.bvcott.booking.dto.user.UserDTO;
import com.bvcott.booking.exception.user.UserNotFoundException;
import com.bvcott.booking.model.User;
import com.bvcott.booking.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service @AllArgsConstructor
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepo;
    private final UserConverter userConverter;

    public List<UserDTO> findAll() {
        log.info("findAll triggered.");
        return userRepo
            .findAll()
            .stream()
            .map(userConverter::toDto)
            .collect(Collectors.toList());     
    }

    public UserDTO findUserById(UUID id) {
        log.info("findUserById triggered with ID: {}", id);
        User user = userRepo
            .findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found with the provided id: " + id));
        return userConverter.toDto(user);
    }

}
