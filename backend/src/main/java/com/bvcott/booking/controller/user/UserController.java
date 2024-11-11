package com.bvcott.booking.controller.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bvcott.booking.dto.user.UserDTO;
import com.bvcott.booking.service.user.UserService;

import lombok.AllArgsConstructor;

@RestController @RequestMapping("api/v1/user") @AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> dtos = userService.findAll();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


}
