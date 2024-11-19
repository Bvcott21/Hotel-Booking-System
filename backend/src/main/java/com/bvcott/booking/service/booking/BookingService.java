package com.bvcott.booking.service.booking;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bvcott.booking.converter.booking.BookingConverter;
import com.bvcott.booking.dto.booking.BookingDTO;
import com.bvcott.booking.exception.general.ResourceNotFoundException;
import com.bvcott.booking.model.booking.Booking;
import com.bvcott.booking.repository.booking.BookingRepository;

import lombok.AllArgsConstructor;

@Service @AllArgsConstructor
public class BookingService {
    private static final Logger log = LoggerFactory.getLogger(BookingService.class);
    private final BookingRepository bookingRepo;
    private final BookingConverter bookingConverter;

    public List<BookingDTO> findAll() {
        log.info("findAll triggered");
        return bookingRepo
            .findAll()
            .stream()
            .map(bookingConverter::toDto)
            .collect(Collectors.toList());
    }

    public BookingDTO findById(UUID bookingId) {
        log.info("findById triggered with values: bookingId: {}", bookingId);
        return bookingConverter.toDto(findEntityById(bookingId));
    }

    private Booking findEntityById(UUID bookingId) {
        return bookingRepo
            .findById(bookingId)
            .orElseThrow(() -> 
                new ResourceNotFoundException("Booking notfound with the provided ID."));    
    }
}
