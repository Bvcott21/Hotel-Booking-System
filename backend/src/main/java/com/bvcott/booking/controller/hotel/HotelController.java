package com.bvcott.booking.controller.hotel;

import java.util.List;
import java.util.UUID;

import com.bvcott.booking.model.hotel.Hotel;
import com.bvcott.booking.model.hotel.HotelRoom;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bvcott.booking.dto.hotel.HotelCreateUpdateDTO;
import com.bvcott.booking.dto.hotel.HotelUpdateDiscountDTO;
import com.bvcott.booking.service.hotel.HotelService;

import lombok.AllArgsConstructor;

@RestController @AllArgsConstructor
@RequestMapping("/api/v1/hotel")
public class HotelController {
    private final HotelService hotelService;

    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        return ResponseEntity.ok(hotelService.findAll());
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable UUID hotelId) {
        return ResponseEntity.ok(hotelService.findById(hotelId));
    }
    
    @GetMapping("/by-city")
    public ResponseEntity<List<Hotel>> getAllHotelsByCity(@RequestParam String city) {
    	return ResponseEntity.ok(hotelService.findAllByCity(city));
    }

    @PostMapping
    public ResponseEntity<Hotel> createHotel(
        @RequestParam UUID hotelOwnerId,
        @RequestBody HotelCreateUpdateDTO hotel) {
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

    @PutMapping("/{hotelId}")
    public ResponseEntity<Hotel> updateHotel(
        @RequestParam UUID hotelOwnerId,
        @PathVariable UUID hotelId,
        @RequestBody HotelCreateUpdateDTO hotel
    ) {
        return ResponseEntity.ok(hotelService.updateHotel(hotelOwnerId, hotelId, hotel));
    }

    @PutMapping("/room/{roomId}")
    public ResponseEntity<HotelRoom> updateHotelRoom(
        @RequestParam UUID hotelOwnerId,
        @RequestParam UUID hotelId,
        @PathVariable UUID roomId,
        @RequestBody HotelRoom room
    ) {
        return ResponseEntity.ok(hotelService.updateHotelRoom(hotelOwnerId, hotelId, roomId, room));
    }
    
    @PutMapping("/discount/{hotelId}")
    public ResponseEntity<Hotel> updateHotelDiscount(
    		@PathVariable UUID hotelId,
    		@RequestParam UUID hotelOwnerId,
    		@RequestBody HotelUpdateDiscountDTO dto) {
    	return ResponseEntity.ok(hotelService.updateHotelDiscount(hotelId, hotelOwnerId, dto));
    }
    
    
    
}
