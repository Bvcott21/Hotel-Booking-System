package com.bvcott.booking.controller.booking;

import java.util.List;
import java.util.UUID;

import com.bvcott.booking.model.booking.Booking;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bvcott.booking.dto.booking.BookingDTO;
import com.bvcott.booking.service.booking.BookingService;

import lombok.AllArgsConstructor;

@RestController @RequestMapping("/api/v1/booking")
@AllArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    
    @GetMapping
    public ResponseEntity<List<Booking>> findAllBookings() {
        return ResponseEntity.ok(bookingService.findAll());
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> findBookingById(@PathVariable UUID bookingId) {
        return ResponseEntity.ok(bookingService.findById(bookingId));
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking() {return null;}

}
