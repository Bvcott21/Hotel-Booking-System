package com.bvcott.booking.mapping.service.hotel;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bvcott.booking.dto.hotel.HotelDTO;
import com.bvcott.booking.dto.hotel.HotelOccupancyDTO;
import com.bvcott.booking.dto.hotel.room.HotelRoomOccupancyDTO;
import com.bvcott.booking.mapping.service.address.AddressMappingService;
import com.bvcott.booking.mapping.service.hotel.facility.FacilityMappingService;
import com.bvcott.booking.mapping.service.hotel.room.HotelRoomMappingService;
import com.bvcott.booking.model.hotel.Facility;
import com.bvcott.booking.model.hotel.Hotel;

import lombok.AllArgsConstructor;

@Service @AllArgsConstructor
public class HotelMappingService {
	private final AddressMappingService addressMappingService;
	private final HotelRoomMappingService roomMappingService;
	private final FacilityMappingService facilityMappingService;
	
	public HotelDTO toDto(Hotel entity) {
		return HotelDTO.builder()
	            .hotelId(entity.getHotelId())
	            .name(entity.getName())
	            .description(entity.getDescription())
	            .rating(entity.getRating())
	            .address(addressMappingService.toDto(entity.getAddress()))
	            .facilities(entity
	                .getFacilities()
	                .stream()
	                .map(Facility::getDisplayName)
	                .collect(Collectors.toList()))
	            .hotelRooms(entity
	                .getRooms()
	                .stream()
	                .map(roomMappingService::toDto)
	                .collect(Collectors.toList()))
	            .build();
	}
	
	public Hotel toEntity(HotelDTO dto) {
        Hotel entity = new Hotel();

        entity.setHotelId(dto.getHotelId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setRating(dto.getRating());
        entity.setAddress(addressMappingService.toEntity(dto.getAddress()));
        entity.setFacilities(dto
            .getFacilities()
            .stream()
            .map(facilityMappingService::mapStringToFacility)
            .collect(Collectors.toList()));
        entity.setRooms(dto
            .getHotelRooms()
            .stream()
            .map(roomMappingService::toEntity)
            .collect(Collectors.toList()));
        return entity;
    }

	public HotelOccupancyDTO toOccupancyDto(Hotel hotel, List<HotelRoomOccupancyDTO> roomOccupancyDtos) {
		return HotelOccupancyDTO
			.builder()
			.hotelId(hotel.getHotelId())
			.name(hotel.getName())
			.roomOccupancy(roomOccupancyDtos)
			.build();
	}
	
	

    

    
}
