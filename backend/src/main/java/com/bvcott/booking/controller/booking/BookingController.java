package com.bvcott.booking.controller.booking;

import java.util.List;
import java.util.UUID;

import com.bvcott.booking.dto.booking.CreateBookingAndTransactionDTO;
import com.bvcott.booking.model.booking.Booking;
import com.bvcott.booking.model.payment.BookingTransaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<BookingTransaction> createBooking(
            @RequestParam UUID customerId,
            @RequestBody CreateBookingAndTransactionDTO dto) {
        return ResponseEntity.ok(bookingService.createBookings(customerId, dto));
    }

}
