package com.bvcott.booking.service.user;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bvcott.booking.exception.general.ResourceNotFoundException;
import com.bvcott.booking.model.user.User;
import com.bvcott.booking.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service @AllArgsConstructor
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepo;

    public List<User> findAll() {
        log.info("findAll triggered.");
        return userRepo.findAll();
    }

    public User findUserById(UUID id) {
        log.info("findUserById triggered with ID: {}", id);
        return userRepo
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with the provided id: " + id));
    }

}
