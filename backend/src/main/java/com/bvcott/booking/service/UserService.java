package com.bvcott.booking.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.bvcott.booking.converter.user.UserConverter;
import com.bvcott.booking.dto.user.UserCreateDTO;
import com.bvcott.booking.dto.user.UserDTO;
import com.bvcott.booking.exception.user.UserNotFoundException;
import com.bvcott.booking.exception.user.UserTypeNotAllowedException;
import com.bvcott.booking.model.Administrator;
import com.bvcott.booking.model.User;
import com.bvcott.booking.repository.UserRepository;

public class UserService {
    @Autowired private UserRepository userRepo;
    @Autowired private UserConverter userConverter;

    public List<UserDTO> findAll() {
        return userRepo
            .findAll()
            .stream()
            .map(user -> userConverter.toDto(user))
            .collect(Collectors.toList());     
    }

    public UserDTO createUser(UserCreateDTO dto) {
        if(dto.getUserType().equals("admin")) {
            Administrator newAdmin = new Administrator();
            newAdmin.setUsername(dto.getUsername());
            newAdmin.setPassword(dto.getPassword());
            Administrator savedAdmin = userRepo.save(newAdmin);
            UserDTO savedUserDTO = userConverter.toDto(savedAdmin);
            return savedUserDTO;
        }
        throw new UserTypeNotAllowedException("User type defined not allowed");
    }

    public UserDTO findUserById(UUID id) {
        User user = userRepo
            .findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found with the provided id: " + id));
        return userConverter.toDto(user);
    }

}
