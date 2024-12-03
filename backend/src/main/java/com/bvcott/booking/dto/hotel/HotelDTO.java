package com.bvcott.booking.dto.hotel;

import java.util.List;
import java.util.UUID;

import com.bvcott.booking.dto.address.AddressDTO;
import com.bvcott.booking.dto.hotel.room.HotelRoomDTO;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class HotelDTO {
    private UUID hotelId;
    private String name;
    private String description;
    private double rating;
    private double discount;
    private AddressDTO address;
    private List<String> facilities;
    private List<HotelRoomDTO> hotelRooms;
}
