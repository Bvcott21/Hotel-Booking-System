package com.bvcott.booking.model.hotel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.bvcott.booking.model.address.Address;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity @Data
public class Hotel {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID hotelId;

    @NotBlank(message = "Hotel name must not be blank")
    @Size(min = 5, max = 35, message = "Hotel name must be between 5 and 35 characters")
    private String name;

    @NotBlank(message = "Hotel description must not be blank")
    @Size(max = 5000, message = "Hotel description must not exceed 5000 characters.")
    private String description;

    @Min(value = 0, message = "Hotel rating must be at least 0")
    @Max(value = 5, message = "Hotel rating must not exceed 5")
    private double rating;
    
    @NotNull(message = "Hotel address must be provided")
    @Embedded private Address address;
    
    @ElementCollection private List<Facility> facilities = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HotelRoom> rooms = new ArrayList<>();

    public void addHotelRoom(HotelRoom room) {
        rooms.add(room);
    }
}
