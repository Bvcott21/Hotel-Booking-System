package com.bvcott.booking.controller.user;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bvcott.booking.dto.user.UserDTO;
import com.bvcott.booking.dto.user.hotelOwner.HotelOwnerCreateDTO;
import com.bvcott.booking.dto.user.hotelOwner.HotelOwnerDTO;
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

    @PostMapping("/create/hotel-owner/{adminId}")
    public ResponseEntity<HotelOwnerDTO> createHotelOwner(
        @PathVariable UUID adminId, 
        @RequestBody HotelOwnerCreateDTO dto) {
            HotelOwnerDTO createdDto = adminService.createHotelOwner(adminId, dto);
            return new ResponseEntity<>(createdDto, HttpStatus.CREATED); 
    }

    @DeleteMapping("/delete/hotel-owner/{hotelOwnerId}")
    public ResponseEntity<String> deleteHotelOwner(
        @RequestParam UUID adminId,
        @PathVariable UUID hotelOwnerId) {
        adminService.deleteHotelOwner(adminId, hotelOwnerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
