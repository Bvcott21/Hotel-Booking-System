package com.bvcott.booking.model.hotel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.bvcott.booking.model.address.Address;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity @Data
public class Hotel {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID hotelId;
    private String name;
    private String description;
    @Embedded private Address address;
    @ElementCollection private List<Facility> facilities = new ArrayList<>();
}
