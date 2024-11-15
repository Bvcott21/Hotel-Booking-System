package com.bvcott.booking.model.hotel;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Entity @Data
public class HotelRoom {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID hotelRoomId;

    @Enumerated(EnumType.STRING) 
    private HotelRoomType roomType;

    @PositiveOrZero(message = "Price cannot be negative")
    private double price;
    
    private boolean isAvailable;
    private LocalDate checkin;
    private LocalDate checkout;
}
