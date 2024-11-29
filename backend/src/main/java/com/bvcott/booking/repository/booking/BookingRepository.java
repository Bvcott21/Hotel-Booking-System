package com.bvcott.booking.repository.booking;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bvcott.booking.model.booking.Booking;

public interface BookingRepository extends JpaRepository<Booking, UUID>{
    
	@Query("SELECT b FROM Booking b WHERE b.hotel.hotelId = :hotelId AND b.checkin <= :endDate AND b.checkout >= :startDate")
	List<Booking> findBookingsForHotelWithinDateRange(
    	@Param("hotelId") UUID hotelId,
    	@Param("startDate") LocalDate startDate,
    	@Param("endDate") LocalDate endDate
    );
}
