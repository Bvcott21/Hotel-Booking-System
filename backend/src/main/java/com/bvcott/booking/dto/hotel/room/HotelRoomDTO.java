package com.bvcott.booking.dto.hotel.room;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class HotelRoomDTO {
    private UUID hotelRoomId;
    private String roomType;
    private double price;
    private boolean isAvailable;
    private LocalDate checkin;
    private LocalDate checkout;
}
