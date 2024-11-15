package com.bvcott.booking.repository.hotel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import com.bvcott.booking.model.hotel.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, UUID> {

}
