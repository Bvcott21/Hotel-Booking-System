package com.bvcott.booking.converter.hotel.facility;

import org.springframework.stereotype.Component;

import com.bvcott.booking.mapping.service.hotel.facility.FacilityMappingService;
import com.bvcott.booking.model.hotel.Facility;

import lombok.AllArgsConstructor;

@Component @AllArgsConstructor
public class FacilityConverter {
	private final FacilityMappingService facilityMappingService;
	
	public Facility mapStringToFacility(String displayName) {
		return facilityMappingService.mapStringToFacility(displayName);
	}
}
