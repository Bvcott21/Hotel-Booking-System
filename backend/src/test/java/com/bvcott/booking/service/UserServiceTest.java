package com.bvcott.booking.service;

import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.bvcott.booking.dto.user.UserDTO;
import com.bvcott.booking.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock private UserRepository userRepo;
    @Autowired @InjectMocks private UserService userService;

    @BeforeEach public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test public void testFindAllUsers_callsUserRepoFindAll() {
        userService.findAll();
        verify(userRepo).findAll();
    }
    
}
