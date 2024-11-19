package com.bvcott.booking.model.booking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.bvcott.booking.model.hotel.Hotel;
import com.bvcott.booking.model.hotel.HotelRoom;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity @Data
public class Booking {
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID bookingId;

        @ManyToOne
        private Hotel hotel;

        @ManyToMany
        private List<HotelRoom> rooms = new ArrayList<>();

        @NotNull
        private LocalDate checkin;

        @NotNull
        private LocalDate checkout;

        @NotNull
        private double price;
}
