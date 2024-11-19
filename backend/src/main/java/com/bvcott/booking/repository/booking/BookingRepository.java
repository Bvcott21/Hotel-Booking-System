package com.bvcott.booking.repository.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bvcott.booking.model.booking.Booking;

import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID>{
    
}
