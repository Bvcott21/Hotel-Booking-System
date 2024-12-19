package com.bvcott.booking.dto.booking;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder @Data
public class BookingCreateDTO {
    private UUID hotelId;
    private List<UUID> hotelRoomIds;
    private LocalDate checkin, checkout;
    private double price;
}
