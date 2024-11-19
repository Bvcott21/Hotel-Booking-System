package com.bvcott.booking.mapping.service.hotel.facility;

import org.springframework.stereotype.Service;

import com.bvcott.booking.model.hotel.Facility;

@Service
public class FacilityMappingService {
	public Facility mapStringToFacility(String displayName) {
        for (Facility facility :Facility.values()) {
            if (facility.getDisplayName().equalsIgnoreCase(displayName)) {
                return facility;
            }
        }

        throw new IllegalArgumentException("Invalid facility name: " + displayName);
    }
}
