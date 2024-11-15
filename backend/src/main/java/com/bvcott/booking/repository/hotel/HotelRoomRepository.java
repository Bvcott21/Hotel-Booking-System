package com.bvcott.booking.repository.hotel;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bvcott.booking.model.hotel.HotelRoom;

public interface HotelRoomRepository extends JpaRepository<HotelRoom, UUID> {

}
