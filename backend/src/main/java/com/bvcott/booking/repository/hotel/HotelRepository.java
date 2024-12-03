package com.bvcott.booking.repository.hotel;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bvcott.booking.model.hotel.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, UUID> {
	List<Hotel> findByAddressCityIgnoreCase(String city);
}
