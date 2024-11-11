package com.bvcott.booking.controller.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bvcott.booking.dto.user.UserDTO;
import com.bvcott.booking.service.user.AdminService;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    private final AdminService adminService;
    
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/find/hotel-owner/all")
    public ResponseEntity<List<UserDTO>> findAllHotelOwners() {
        List<UserDTO> hotelOwnersDTOs = adminService.findAllHotelOwners();
        return new ResponseEntity<>(hotelOwnersDTOs, HttpStatus.OK);
    }
}
