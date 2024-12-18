package com.bvcott.booking.service.booking;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bvcott.booking.exception.general.ResourceNotFoundException;
import com.bvcott.booking.model.booking.Booking;
import com.bvcott.booking.repository.booking.BookingRepository;

import lombok.AllArgsConstructor;

@Service @AllArgsConstructor
public class BookingService {
    private static final Logger log = LoggerFactory.getLogger(BookingService.class);
    private final BookingRepository bookingRepo;

    public List<Booking> findAll() {
        log.info("findAll triggered");
        return bookingRepo
            .findAll();
    }

    public Booking findById(UUID bookingId) {
        return bookingRepo
            .findById(bookingId)
            .orElseThrow(() -> 
                new ResourceNotFoundException("Booking notfound with the provided ID."));    
    }
}
