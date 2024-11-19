package com.bvcott.booking.dto.booking;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.bvcott.booking.dto.hotel.HotelDTO;
import com.bvcott.booking.dto.hotel.HotelRoomDTO;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class BookingDTO {
    private UUID bookingId;
    private HotelDTO hotel;
    private List<HotelRoomDTO> rooms;
    private LocalDate checkin;
    private LocalDate checkout;
    private double price;
}
