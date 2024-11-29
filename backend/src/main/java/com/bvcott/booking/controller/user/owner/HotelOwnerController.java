package com.bvcott.booking.controller.user.owner;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bvcott.booking.dto.hotel.HotelOccupancyDTO;
import com.bvcott.booking.service.user.HotelOwnerService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/owner")
@AllArgsConstructor
public class HotelOwnerController {
	private final HotelOwnerService ownerService;
	
	@GetMapping("/{ownerId}/occupancy")
	public ResponseEntity<List<HotelOccupancyDTO>> getHotelOccupancyAndBookings(
			@PathVariable UUID ownerId,
			@RequestParam(required = false) LocalDate date) {
		LocalDate currentDate = (date == null) ? LocalDate.now() : date;
		List<HotelOccupancyDTO> occupancyDetails = ownerService.getHotelOccupancyAndBookings(ownerId, currentDate);
		return ResponseEntity.ok(occupancyDetails);	
	}
	
	
}
