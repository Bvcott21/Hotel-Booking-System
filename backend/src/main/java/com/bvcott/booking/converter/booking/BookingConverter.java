package com.bvcott.booking.converter.booking;

import org.springframework.stereotype.Component;

import com.bvcott.booking.dto.booking.BookingDTO;
import com.bvcott.booking.mapping.service.booking.BookingMappingService;
import com.bvcott.booking.model.booking.Booking;

import lombok.AllArgsConstructor;

@Component @AllArgsConstructor
public class BookingConverter {
    private final BookingMappingService bookingMappingService;

    public BookingDTO toDto(Booking entity) {
        return bookingMappingService.toDto(entity);
    }

    public Booking toEntity(BookingDTO dto) {
        return bookingMappingService.toEntity(dto);
    }
}
