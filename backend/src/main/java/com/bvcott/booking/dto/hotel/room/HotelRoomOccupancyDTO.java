package com.bvcott.booking.dto.hotel.room;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class HotelRoomOccupancyDTO {
	private UUID hotelRoomId;
	private String roomType;
	private boolean isOccupied;
	private LocalDate checkin;
	private LocalDate checkout;
}
