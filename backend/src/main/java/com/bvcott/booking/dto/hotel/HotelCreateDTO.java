package com.bvcott.booking.dto.hotel;

import java.util.List;

import com.bvcott.booking.dto.address.AddressDTO;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class HotelCreateDTO {
    private String name;
    private String description;
    private AddressDTO address;
    private List<String> facilities;

    private int numberOfSingleRooms;
    private double priceOfSingleRooms;

    private int numberOfDoubleRooms;
    private double priceOfDoubleRooms;

    private int numberOfSuiteRooms;
    private double priceOfSuiteRooms;

    private int numberOfFamilyRooms;
    private double priceOfFamilyRooms;

    private int numberOfDeluxeRooms;
    private double priceOfDeluxeRooms;
}
