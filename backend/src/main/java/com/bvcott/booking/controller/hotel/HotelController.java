package com.bvcott.booking.controller.hotel;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bvcott.booking.dto.hotel.HotelCreateDTO;
import com.bvcott.booking.dto.hotel.HotelDTO;
import com.bvcott.booking.service.hotel.HotelService;

import lombok.AllArgsConstructor;

@RestController @AllArgsConstructor
@RequestMapping("/api/v1/hotel")
public class HotelController {
    private final HotelService hotelService;

    @GetMapping
    public ResponseEntity<List<HotelDTO>> getAllHotels() {
        return ResponseEntity.ok(hotelService.findAll());
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable UUID hotelId) {
        return ResponseEntity.ok(hotelService.findById(hotelId));
    }

    @PostMapping
    public ResponseEntity<HotelDTO> createHotel(
        @RequestParam UUID hotelOwnerId,
        @RequestBody HotelCreateDTO hotel) {
            return ResponseEntity.ok(hotelService.createHotel(hotelOwnerId, hotel));
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> deleteHotel(
        @RequestParam UUID hotelOwnerId,
        @PathVariable UUID hotelId
    ) {
        hotelService.deleteHotel(hotelOwnerId, hotelId);
        return ResponseEntity.noContent().build();
    }
}
